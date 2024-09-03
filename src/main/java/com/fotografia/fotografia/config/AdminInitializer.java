package com.fotografia.fotografia.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fotografia.fotografia.models.Admin;
import com.fotografia.fotografia.repositories.AdminRepository;

@Component
public class AdminInitializer {
    @Bean
    CommandLineRunner init(AdminRepository AdminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(AdminRepository.findByUsername("admin").isEmpty()){
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpassword"));
                AdminRepository.save(admin);
            }
        };
    }
}
