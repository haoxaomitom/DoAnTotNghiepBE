package com.example.doantotnghiepbe.controller;

import com.cloudinary.Cloudinary;
import com.example.doantotnghiepbe.dto.ImageDTO;
import com.example.doantotnghiepbe.dto.ImagesDTO;
import com.example.doantotnghiepbe.service.ImageService;
import com.example.doantotnghiepbe.service.UpPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ImageController {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UpPostService postService;
    @Autowired
    private ImageService imageService;

    // API upload ảnh
    @PostMapping("/upload")
    public ResponseEntity<ImagesDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("postId") Integer postId) {
        if (postId == null) {
            System.out.println("postId is null");
        }
        ImagesDTO imageDTO = imageService.uploadImage(file, postId);
        return ResponseEntity.ok(imageDTO);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ImagesDTO>> getImagesByPostId(@PathVariable Integer postId) {
        List<ImagesDTO> images = imageService.getImagesByPostId(postId);
        return ResponseEntity.ok(images);
    }
//    @PostMapping("/upload")
//    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") MultipartFile[] files) {
//        List<String> uploadedUrls = new ArrayList<>();
//        try {
//            for (MultipartFile file : files) {
//                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//                String url = (String) uploadResult.get("secure_url");
//                uploadedUrls.add(url);
//
//            }
//            return ResponseEntity.ok(uploadedUrls);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//
//    @DeleteMapping("/delete/{publicId}")
//    public ResponseEntity<String> deleteImage(@PathVariable String publicId) {
//        try {
//            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
//            return ResponseEntity.ok("Deleted successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Failed to delete image");
//        }
//    }

//    @Autowired
//    private CloudinaryService cloudinaryService;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestParam("files") MultipartFile file) {
//        try {
//            String imageUrl = cloudinaryService.uploadImage(file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete/{publicId}")
//    public ResponseEntity<String> deleteImage(@PathVariable String publicId) {
//        try {
//            String result = cloudinaryService.deleteImage(publicId);
//            return ResponseEntity.ok("Image deleted successfully: " + result);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Failed to delete image: " + e.getMessage());
//        }
//    }
}
