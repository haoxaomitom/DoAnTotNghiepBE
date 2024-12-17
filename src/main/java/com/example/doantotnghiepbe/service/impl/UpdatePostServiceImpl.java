package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.example.doantotnghiepbe.configurations.CloudinaryConfig;
import com.example.doantotnghiepbe.dto.UpdatePostDTO;
import com.example.doantotnghiepbe.entity.*;
import com.example.doantotnghiepbe.exceptions.ResourceNotFoundException;
import com.example.doantotnghiepbe.repository.*;
import com.example.doantotnghiepbe.service.UpdatePostService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UpdatePostServiceImpl implements UpdatePostService {

    private final PostRepository postRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final ImageRepository imageRepository;
    private final Cloudinary cloudinary;
    private final ApprovalPostRepository approvalPostRepository;
    private final CloudinaryConfig cloudinaryConfig;

    public UpdatePostServiceImpl(PostRepository postRepository, VehicleTypeRepository vehicleTypeRepository, AmenitiesRepository amenitiesRepository, ImageRepository imageRepository, Cloudinary cloudinary, ApprovalPostRepository approvalPostRepository, CloudinaryConfig cloudinaryConfig) {
        this.postRepository = postRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
        this.approvalPostRepository = approvalPostRepository;
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Transactional
    public Post updatePost(Integer postId, UpdatePostDTO postRequest) {
        // Retrieve the existing post
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Map fields from postRequest to existingPost
        existingPost.setParkingName(postRequest.getParkingName());
        existingPost.setDescription(postRequest.getDescription());
        existingPost.setStreet(postRequest.getStreet());
        existingPost.setWardName(postRequest.getWardName());
        existingPost.setDistrictName(postRequest.getDistrictName());
        existingPost.setProvinceName(postRequest.getProvinceName());
        existingPost.setPrice(postRequest.getPrice());
        existingPost.setPriceUnit(postRequest.getPriceUnit());
        existingPost.setCapacity(postRequest.getCapacity());
        existingPost.setLatitude(postRequest.getLatitude());
        existingPost.setLongitude(postRequest.getLongitude());
        existingPost.setStatus("WAITING"); // Default status value

        // Save the updated post
        Post updatedPost = postRepository.save(existingPost);

        // Save VehicleTypes
        if (postRequest.getVehicleTypes() != null) {
            vehicleTypeRepository.deleteVehicleTypeByPostPostId(postId); // Remove existing types
            postRequest.getVehicleTypes().forEach(vehicleTypeDTO -> {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypesName(vehicleTypeDTO.getVehicleTypesName());
                vehicleType.setPost(updatedPost);
                vehicleTypeRepository.save(vehicleType);
            });
        }

        // Save Amenities
        if (postRequest.getAmenities() != null) {
            amenitiesRepository.deleteAmenitiesByPostPostId(postId); // Remove existing amenities
            postRequest.getAmenities().forEach(amenitiesDTO -> {
                Amenities amenities = new Amenities();
                amenities.setAmenitiesName(amenitiesDTO.getAmenitiesName());
                amenities.setPost(updatedPost);
                amenitiesRepository.save(amenities);
            });
        }

        // Insert into ApprovalPost (if needed)
        ApprovalPost approvalPost = new ApprovalPost();
        approvalPost.setPost(updatedPost); // Associate with the updated post
        approvalPost.setStatus("WAITING"); // Default status
        approvalPostRepository.save(approvalPost); // Save to repository

        return updatedPost;
    }


    // UpdatePostServiceImpl.java
    @Transactional
    public void updateImages(Integer postId, List<String> deletedImages, List<MultipartFile> newImages) {
        System.out.println("Run upload img");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        List<Image> currentImages = imageRepository.findByPost(post);

        // Handle image deletions
        if (deletedImages != null && !deletedImages.isEmpty()) {
            System.out.println("Run delete img");
            deletedImages.forEach(url -> {
                try {
                    System.out.println("Into try catch");
                    String publicId = cloudinaryConfig.extractPublicId(url); // Gọi phương thức từ CloudinaryConfig
                    cloudinaryConfig.deleteImage(publicId); // Xóa ảnh từ Cloudinary

                    // Xóa ảnh khỏi database
                    System.out.println("URL img: " + url);
                    if (url.startsWith("\"") && url.endsWith("\"")) {
                        url = url.substring(1, url.length() - 1);
                    }

                    System.out.println("Processed URL: " + url);

                    imageRepository.deleteByImageUrl(url); // Sử dụng phương thức xóa ảnh theo URL
                } catch (Exception e) {
                    System.err.println("Error deleting image: " + url + ". " + e.getMessage());
                }
            });
        }

        // Handle new image uploads
        if (newImages != null && !newImages.isEmpty()) {
            newImages.forEach(file -> {
                try {
                    String uploadedUrl = cloudinaryConfig.saveToCloudinary(file); // Tải ảnh lên Cloudinary
                    Image newImage = new Image();
                    newImage.setImageUrl(uploadedUrl);
                    newImage.setPost(post);
                    imageRepository.save(newImage); // Lưu vào DB
                } catch (IOException e) {
                    System.err.println("Error uploading new image: " + e.getMessage());
                }
            });
        }
    }


}
