package com.fotografia.fotografia.services;



import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.security.AdminSecurity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Admin no encontrado");
        }
        return new AdminSecurity(admin); // Devuelve el admin autenticado
    }

}