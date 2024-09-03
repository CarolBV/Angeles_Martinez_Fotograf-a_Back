package com.fotografia.fotografia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fotografia.fotografia.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

    
}