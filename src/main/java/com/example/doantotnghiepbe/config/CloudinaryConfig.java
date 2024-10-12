package com.example.doantotnghiepbe.config;

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
                "cloud_name", "dqzfuomvj",
                "api_key", "141698136898829",
                "api_secret", "95bIAsYzltuLNsF4b0sfYUFrh6Y",
                "secure", true
        );
        return new Cloudinary(config);
    }
}
