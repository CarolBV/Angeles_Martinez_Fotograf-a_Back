package com.fotografia.fotografia.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fotografia.errors.GalleryException;
import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.models.Gallery;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.repositories.GalleryRepository;
import com.fotografia.fotografia.security.AdminSecurity;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Gallery saveImage(Gallery gallery) {
        // Obtener el admin autenticado desde el token
        Admin authenticatedAdmin = getAuthenticatedAdmin();
    
        if (authenticatedAdmin == null) {
            throw new GalleryException("Admin no autenticado.", HttpStatus.UNAUTHORIZED);
        }
    
        // Verificar si el admin existe en la base de datos
        Admin existingAdmin = adminRepository.findById(authenticatedAdmin.getId())
                .orElseThrow(() -> new GalleryException("Admin no encontrado.", HttpStatus.NOT_FOUND));
    
        // Asignar el admin autenticado a la imagen
        gallery.setAdmin(existingAdmin);
    
        // Verificar si la imagen ya existe
        Optional<Gallery> existingGallery = galleryRepository.findByNameAndCategoryAndImageUrlAndAdmin(
                gallery.getName(),
                gallery.getCategory(),
                gallery.getImageUrl(),
                existingAdmin);
    
        if (existingGallery.isPresent()) {
            throw new GalleryException("Una imagen con el mismo título, categoría y URL ya existe en el sistema.", HttpStatus.CONFLICT);
        }
    
        // Guardar la imagen
        return galleryRepository.save(gallery);
    }
    
    private Admin getAuthenticatedAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AdminSecurity) {
            AdminSecurity adminSecurity = (AdminSecurity) authentication.getPrincipal();
            return adminSecurity.getAdmin();
        }
        return null;
    }
    
    public void deleteImage (int id) {
        Optional<Gallery> gallery = galleryRepository.findById(id);
        if(gallery.isPresent()) {
            galleryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Esta imagen no existe");
        }
    }
}
