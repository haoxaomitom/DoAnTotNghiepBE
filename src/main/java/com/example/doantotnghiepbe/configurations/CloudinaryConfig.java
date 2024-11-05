package com.example.doantotnghiepbe.configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private Cloudinary cloudinary;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dcsmxzihc",
                "api_key", "611115153438512",
                "api_secret", "6TVqVT5KaJShiHBZoUakjLZ9W3E");
        return new Cloudinary(config);
    }


    public String saveToCloudinary(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new IOException("Invalid file format");
        }


        String contentType = file.getContentType();
        System.out.println("Content Type: " + contentType);
        if (contentType == null) {
            throw new IOException("Unknown file type");
        }

        String resourceType = "auto";
        if (contentType.startsWith("image/")) {
            resourceType = "image";
        } else if (contentType.startsWith("video/")) {
            resourceType = "video";
        } else if (contentType.startsWith("audio/")) {
            resourceType = "audio";
        }

        Map<String, Object> params = ObjectUtils.asMap(
                "resource_type", resourceType);

        Map result = cloudinary.uploader().upload(file.getBytes(), params);
        return (String) result.get("secure_url");
    }

    private String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        String[] parts = filename.split("\\.");
        return parts.length > 1 ? parts[parts.length - 1] : "";
    }
}