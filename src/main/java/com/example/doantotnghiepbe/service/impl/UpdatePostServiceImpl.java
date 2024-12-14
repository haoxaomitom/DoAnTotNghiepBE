package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.configurations.CloudinaryConfig;
import com.example.doantotnghiepbe.dto.UpdatePostDTO;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.VehicleType;
import com.example.doantotnghiepbe.repository.AmenitiesRepository;
import com.example.doantotnghiepbe.repository.ImageRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.VehicleTypeRepository;
import com.example.doantotnghiepbe.service.UpdatePostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UpdatePostServiceImpl implements UpdatePostService {

    private final PostRepository postRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final ImageRepository imageRepository;
    private final Cloudinary cloudinary;

    public UpdatePostServiceImpl(PostRepository postRepository, VehicleTypeRepository vehicleTypeRepository, AmenitiesRepository amenitiesRepository, ImageRepository imageRepository, Cloudinary cloudinary) {
        this.postRepository = postRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
    }

    @Transactional
    public Post updatePost(Integer postId, UpdatePostDTO postRequest) {
        // Retrieve the existing post
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Use ModelMapper to map fields from postRequest to existingPost
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
        existingPost.setStatus("WAITING");

        // Update associated VehicleTypes if provided
        if (postRequest.getVehicleTypes() != null) {
            // Remove existing VehicleTypes for the post
            vehicleTypeRepository.deleteVehicleTypeByPostPostId(postId);

            // Add new VehicleTypes
            postRequest.getVehicleTypes().forEach(vehicleTypeDTO -> {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypesName(vehicleTypeDTO.getVehicleTypesName());
                vehicleType.setPost(existingPost);
                vehicleTypeRepository.save(vehicleType);
            });
        }

        // Update associated Amenities if provided
        if (postRequest.getAmenities() != null) {
            // Remove existing Amenities for the post
            amenitiesRepository.deleteAmenitiesByPostPostId(postId);

            // Add new Amenities
            postRequest.getAmenities().forEach(amenitiesDTO -> {
                Amenities amenities = new Amenities();
                amenities.setAmenitiesName(amenitiesDTO.getAmenitiesName());
                amenities.setPost(existingPost);
                amenitiesRepository.save(amenities);
            });
        }

        // Save the updated post
        return postRepository.save(existingPost);
    }



    // Service Implementation
    @Transactional
    public List<String> uploadImages(Integer postId, List<MultipartFile> imageFiles) {
        // Retrieve the post object by postId
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Retrieve current images for the post from the database
        List<Image> currentImages = imageRepository.findByPost(post);
        List<String> currentImageUrls = currentImages.stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());

        // Prepare a list to hold the updated image URLs (start with current images)
        List<String> updatedImageUrls = new ArrayList<>(currentImageUrls);
        // Validate the new image files and upload them
        for (MultipartFile file : imageFiles) {
            try {
                // Validate file type (e.g., checking if it's an image file)
                if (!isValidImageFile(file)) {
                    throw new RuntimeException("Invalid file type. Only image files are allowed.");
                }

                // Upload the image to Cloudinary (or another service)
                var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");

                // Save the new image to the database
                Image newImage = new Image();
                newImage.setPost(post);
                newImage.setImageUrl(imageUrl);
                imageRepository.save(newImage);

                // Add the new image URL to the list of updated URLs
                updatedImageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        // Handle image removal: Remove images no longer associated with the post
        removeOldImages(post, currentImageUrls, updatedImageUrls);

        return updatedImageUrls;
    }

    // Helper method to check if the file is a valid image
    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/"));
    }

    // Helper method to remove old images no longer associated with the post
    private void removeOldImages(Post post, List<String> currentImageUrls, List<String> updatedImageUrls) {
        // Find the images that are no longer part of the updated list
        for (String currentImageUrl : currentImageUrls) {
            if (!updatedImageUrls.contains(currentImageUrl)) {
                // Remove the image from Cloudinary (if needed)
                String publicId = extractPublicId(currentImageUrl);
                if (publicId != null) {
                    try {
                        cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "auto"));
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete image from Cloudinary", e);
                    }
                }

                // Remove the image from the database
                Image imageToDelete = imageRepository.findByImageUrlAndPost(currentImageUrl, post)
                        .orElseThrow(() -> new RuntimeException("Image not found in database"));
                imageRepository.delete(imageToDelete);
            }
        }
    }

    // Helper method to extract the publicId from the image URL
    private String extractPublicId(String imageUrl) {
        String[] urlParts = imageUrl.split("/image/upload/");
        if (urlParts.length < 2) {
            return null;
        }
        String publicIdWithExtension = urlParts[1].split("\\.")[0];
        return publicIdWithExtension;
    }

}
