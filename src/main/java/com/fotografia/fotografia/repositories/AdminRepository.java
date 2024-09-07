package com.fotografia.fotografia.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fotografia.fotografia.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
   Optional<Admin> findByUsername(String username);

}

