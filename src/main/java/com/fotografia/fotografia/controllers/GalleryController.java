package com.fotografia.fotografia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fotografia.fotografia.models.Image;
import com.fotografia.fotografia.repositories.ImageRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @PostMapping
    public Image addImage(@RequestBody Image image) {
        return imageRepository.save(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestBody Image imageDetails) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la imagn con id :: " + id));

        image.setName(imageDetails.getName());
        image.setImageUrl(imageDetails.getImageUrl());
        final Image updatedImage = imageRepository.save(image);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la imagen con id :: " + id));

        imageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }
}