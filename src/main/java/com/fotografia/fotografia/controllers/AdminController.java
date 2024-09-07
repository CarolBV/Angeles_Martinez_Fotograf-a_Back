package com.fotografia.fotografia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.repositories.AdminRepository;
import com.fotografia.fotografia.services.AdminDetailsService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminDetailsService adminDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ADMIN");  // Aquí asignas el rol
        Admin saveAdmin = adminRepository.save(admin);
        return new ResponseEntity<>(saveAdmin, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody AdminLoginRequest adminLoginRequest) {
        try {
            AuthResponse response = adminDetailsService.login(adminLoginRequest);
      return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
}
}
