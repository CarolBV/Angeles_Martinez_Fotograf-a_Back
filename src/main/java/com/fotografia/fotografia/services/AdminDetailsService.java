package com.fotografia.fotografia.services;


import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fotografia.errors.UnauthorizedException;
import com.fotografia.errors.UserNotFoundException;
import com.fotografia.fotografia.controllers.AdminLoginRequest;
import com.fotografia.fotografia.controllers.AuthResponse;
import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.repositories.AdminRepository;

import com.fotografia.fotografia.security.JwtUtil;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con este nombre de usuario"));

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtUtil.generateToken(admin);
        return AuthResponse.builder()
                .userId(admin.getId())
                .token(token)
                .build();
    }

    public int getUserId(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return adminRepository.findByUsername(username)
                    .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado")).getId();

        } catch (JwtException e) {
            throw new UnauthorizedException("Token inválido o expirado");

        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Usuario no encontrado para el token proporcionado");
        }
    }
}