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
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqzfuomvj",
                "api_key", "141698136898829",
                "api_secret", "95bIAsYzltuLNsF4b0sfYUFrh6Y",
                "secure", true
        ));
        return this.cloudinary;
    }

    public String saveToCloudinary(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new IOException("Invalid file format");
        }
        String contentType = file.getContentType();
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

    public String requestDelete(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IOException("Invalid public ID");
        }

        // Call the destroy method to delete the image/video
        Map<String, Object> params = ObjectUtils.asMap(
                "resource_type", "auto" // Auto detects the resource type (image/video)
        );

        Map result = cloudinary.uploader().destroy(publicId, params);

        // Check the result and return response message
        if (result.containsKey("result") && result.get("result").equals("ok")) {
            return "Deletion successful";
        } else {
            throw new IOException("Failed to delete the image. " + result.get("error"));
        }
    }
}
