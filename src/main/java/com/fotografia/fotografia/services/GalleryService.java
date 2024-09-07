package com.fotografia.fotografia.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import com.fotografia.errors.RuntimeErrorsException;

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

    public Gallery saveImage(Gallery gallery) {
    return galleryRepository.save(gallery);
   
    }
    
    public Gallery addImageToUser (Admin admin, Gallery gallery) {
        gallery.setAdmin(admin);
        return galleryRepository.save(gallery);
    }

    public Optional<Gallery> getImageByIdAndAdminId(Long galleryId, int adminId) {
        return galleryRepository.findByIdAndAdminId(galleryId, adminId);
    }

    public Gallery getImage(Integer adminId, Long galleryId) {
        try{
            Optional<Gallery> existingImage = getImageByIdAndAdminId(galleryId, adminId);
            Gallery gallery = existingImage.get();
            return gallery;
        }catch (Error er) {
            throw new RuntimeErrorsException(er);
        }
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
