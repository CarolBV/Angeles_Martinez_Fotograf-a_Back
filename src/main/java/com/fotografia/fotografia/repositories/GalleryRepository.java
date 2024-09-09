package com.fotografia.fotografia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.models.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long>{

    Optional<Gallery> findById(Long id);

    void deleteById(Long id);
    Optional<Gallery> findByNameAndCategoryAndImageUrlAndAdmin(String name, String category, String imageUrl, Admin admin);
    Optional<Gallery> findByIdAndAdminId(Long galleryId, Integer adminId);
    List<Gallery> findByAdminId(Integer adminId);
}