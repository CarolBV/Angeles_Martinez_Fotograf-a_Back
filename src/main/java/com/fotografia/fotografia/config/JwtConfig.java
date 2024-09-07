package com.fotografia.fotografia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fotografia.fotografia.security.JwtUtil;
import com.fotografia.fotografia.services.CustomAdminDetailsService;

@Configuration
public class JwtConfig {
    
     @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, CustomAdminDetailsService customAdminDetailsService) {
        return new JwtAuthenticationFilter(jwtUtil, customAdminDetailsService);
}
} 
    

