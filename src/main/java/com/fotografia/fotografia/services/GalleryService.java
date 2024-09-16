package com.fotografia.fotografia.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.models.Gallery;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.repositories.GalleryRepository;


@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private AdminRepository adminRepository;

    public void saveImage(String name, String category, String imageUrl, String publicId, String username) {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        Gallery gallery = new Gallery();
        gallery.setName(name);
        gallery.setCategory(category);
        gallery.setImageUrl(imageUrl);
        gallery.setCloudinaryPublicId(publicId);
        gallery.setAdmin(admin);

        galleryRepository.save(gallery);
    }

    
    public Gallery addImageToUser (Admin admin, Gallery gallery) {
        gallery.setAdmin(admin);
        return galleryRepository.save(gallery);
    }

    public Optional<Gallery> getImageByIdAndAdminId(Long galleryId, int adminId) {
        return galleryRepository.findByIdAndAdminId(galleryId, adminId);
    }

    public Gallery getImage(Integer adminId, Long galleryId) {
        return getImageByIdAndAdminId(galleryId, adminId)
            .orElseThrow(() -> new RuntimeException("Imagen no encontrada o no accesible por el admin"));
    }
}
