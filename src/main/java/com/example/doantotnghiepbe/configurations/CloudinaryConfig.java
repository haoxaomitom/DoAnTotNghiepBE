package com.example.doantotnghiepbe.configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dcsmxzihc",
                "api_key", "611115153438512",
                "api_secret", "6TVqVT5KaJShiHBZoUakjLZ9W3E");
        return new Cloudinary(config);
    }
}