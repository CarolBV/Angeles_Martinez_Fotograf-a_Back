package com.fotografia.fotografia.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import com.fotografia.fotografia.models.Gallery;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.repositories.GalleryRepository;
import com.fotografia.fotografia.services.CloudinaryService;
import com.fotografia.fotografia.services.GalleryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private GalleryService galleryService;
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, String>>> getAllCategoriesWithImages() {
        // Aquí obtenemos todas las categorías distintas
        List<String> categories = galleryRepository.findDistinctCategories();

        // Creamos una lista para almacenar la categoría y su imagen representativa
        List<Map<String, String>> categoryWithImages = new ArrayList<>();

        // Para cada categoría, obtenemos la primera imagen asociada a esa categoría
        for (String category : categories) {
            Optional<Gallery> representativeImage = galleryRepository.findFirstByCategory(category);

            // Creamos un mapa con la categoría y su imagen (si existe)
            Map<String, String> categoryMap = new HashMap<>();
            categoryMap.put("category", category);
            categoryMap.put("imageUrl",
                    representativeImage.isPresent() ? representativeImage.get().getImageUrl() : null);

            categoryWithImages.add(categoryMap);
        }

        return ResponseEntity.ok(categoryWithImages);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Gallery>> getImagesByCategory(@PathVariable String category) {
        List<Gallery> images = galleryRepository.findImagesByCategory(category);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Gallery> getImageById(@PathVariable Long id) {
        Gallery image = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada"));
        return ResponseEntity.ok(image);
    }

    @GetMapping("/random/images")
    public ResponseEntity<List<Gallery>> getRandomImages() {
        List<Gallery> randomImages = galleryRepository.findRandomImages(PageRequest.of(0, 5)); // Por ejemplo, obtenemos
                                                                                               // 5 imágenes aleatorias
        return ResponseEntity.ok(randomImages);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            Authentication authentication) {

        try {
            // Subir la imagen a Cloudinary
            Map uploadResult = cloudinaryService.uploadImage(file);
            String imageUrl = uploadResult.get("url").toString();
            String publicId = uploadResult.get("public_id").toString();

            // Guardar la información en la base de datos
            String username = authentication.getName();
            galleryService.saveImage(name, category, imageUrl, publicId, username);

            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá detalles del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Gallery image = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encuentra la imagen con id :: " + id));

        galleryRepository.delete(image);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/image/{id}")
    public ResponseEntity<Gallery> updateImage(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        try {
            // Buscar la imagen en la base de datos
            Gallery image = galleryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada"));
    
            // Solo permitir que el admin logueado edite la imagen
            String username = authentication.getName();
            if (!image.getAdmin().getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
    
            // Actualizar nombre y categoría
            image.setName(name);
            image.setCategory(category);
    
            // Si se ha subido un nuevo archivo, actualizar la imagen en Cloudinary
            if (file != null && !file.isEmpty()) {
                // Eliminar la imagen antigua de Cloudinary
                cloudinaryService.deleteImage(image.getCloudinaryPublicId());
    
                // Subir la nueva imagen a Cloudinary
                Map uploadResult = cloudinaryService.uploadImage(file);
                String imageUrl = uploadResult.get("url").toString();
                String publicId = uploadResult.get("public_id").toString();
    
                // Actualizar los datos en la base de datos
                image.setImageUrl(imageUrl);
                image.setCloudinaryPublicId(publicId);
            }
    
            // Guardar los cambios en la base de datos
            galleryRepository.save(image);
    
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            // Manejar la excepción en caso de error con Cloudinary o la subida del archivo
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
            }
        }