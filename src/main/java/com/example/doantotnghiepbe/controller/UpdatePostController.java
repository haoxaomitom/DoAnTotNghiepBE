package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.dto.UpdatePostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.UpdatePostService;
import com.example.doantotnghiepbe.service.impl.UpdatePostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/updatePosts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UpdatePostController {

    private final UpdatePostService updatePostService;

    public UpdatePostController(UpdatePostService updatePostService) {
        this.updatePostService = updatePostService;
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer postId,
                                           @RequestBody UpdatePostDTO postRequest) {
        try {
            Post updatedPost = updatePostService.updatePost(postId, postRequest);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/images/upload/{postId}")
    public ResponseEntity<List<String>> uploadImages(
            @PathVariable Integer postId,
            @RequestParam("images") List<MultipartFile> imageFiles) {
        try {
            // Call the service to handle the image upload and update process
            List<String> imageUrls = updatePostService.uploadImages(postId, imageFiles);
            return ResponseEntity.ok(imageUrls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



}
