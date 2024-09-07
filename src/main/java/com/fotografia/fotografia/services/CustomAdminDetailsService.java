package com.fotografia.fotografia.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.security.AdminSecurity;

@Service
public class CustomAdminDetailsService implements UserDetailsService {
    
    private final AdminRepository adminRepository;
    public CustomAdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
      @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Utiliza orElseThrow para manejar la ausencia de Admin en la base de datos
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin no encontrado"));
    
        // Retorna el objeto envuelto en AdminSecurity
        return new AdminSecurity(admin); 
    }
}
