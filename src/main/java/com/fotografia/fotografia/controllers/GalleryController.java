package com.fotografia.fotografia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.models.Gallery;

import com.fotografia.fotografia.repositories.GalleryRepository;
import com.fotografia.fotografia.security.AdminSecurity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
   private GalleryRepository galleryRepository;

    @GetMapping
    public List<Gallery> getAllImages() {
        return galleryRepository.findAll();
    }


@PostMapping("/image")
  public ResponseEntity<Gallery> addImage(@RequestBody Gallery gallery) {
    Gallery savedGallery = galleryRepository.save(gallery);
    return ResponseEntity.ok(savedGallery);
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Gallery image = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la imagen con id :: " + id));

        galleryRepository.delete(image);
        return ResponseEntity.noContent().build();
    }
}