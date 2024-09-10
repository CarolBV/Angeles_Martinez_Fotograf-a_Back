package com.fotografia.fotografia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.models.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long>{

    // Obtener una imagen por ID
    Optional<Gallery> findById(Long id);

    // Eliminar una imagen por ID
    void deleteById(Long id);

    // Obtener imágenes por nombre, categoría, URL de la imagen y el admin
    Optional<Gallery> findByNameAndCategoryAndImageUrlAndAdmin(String name, String category, String imageUrl, Admin admin);

    // Obtener una imagen por ID y el ID del admin
    Optional<Gallery> findByIdAndAdminId(Long galleryId, Integer adminId);

    // Obtener todas las categorías distintas
    @Query("SELECT DISTINCT category FROM Gallery")
    List<String> findDistinctCategories();

    // Obtener todas las imágenes de una categoría
    List<Gallery> findByCategory(String category);

    // Obtener la primera imagen de una categoría (para mostrar la imagen representativa)
    Optional<Gallery> findFirstByCategory(String category);
    
    @Query("SELECT g FROM Gallery g ORDER BY RANDOM()")
    List<Gallery> findRandomImages(Pageable pageable);
}