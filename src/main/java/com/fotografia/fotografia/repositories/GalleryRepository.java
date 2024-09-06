package com.fotografia.fotografia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fotografia.fotografia.models.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Integer>{

    Optional<Gallery> findById(Long id);

    void deleteById(int id);

    
}