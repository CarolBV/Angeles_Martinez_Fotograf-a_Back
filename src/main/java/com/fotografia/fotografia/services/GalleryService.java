package com.fotografia.fotografia.services;
import java.util.Optional;



import org.springframework.stereotype.Service;

import com.fotografia.fotografia.models.Gallery;
import com.fotografia.fotografia.repositories.GalleryRepository;

@Service
public class GalleryService {
    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
    this.galleryRepository = galleryRepository;
    }

    public Gallery saveImage(Gallery gallery) {
    return galleryRepository.save(gallery);
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
