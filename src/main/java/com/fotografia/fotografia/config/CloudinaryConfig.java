package com.fotografia.fotografia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "draix7shx",
                "api_key", "939761669416267",
                "api_secret", "UlN3XKtdl-ri5ZoXTXDzu8Bq6po",
                "secure", true));
    }
}