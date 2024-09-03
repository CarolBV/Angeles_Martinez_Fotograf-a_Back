package com.fotografia.fotografia.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fotografia.fotografia.models.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);

}

