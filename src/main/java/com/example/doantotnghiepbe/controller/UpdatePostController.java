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

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/updatePosts")
@CrossOrigin(origins = "http://127.0.0.1")
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

    @PostMapping("/updateImage/{postId}")
    public ResponseEntity<?> updateImages(
            @PathVariable Integer postId,
            @RequestParam(value = "deletedImages", required = false) List<String> deletedImages, // Nhận từ FormData
            @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages) { // Nhận từ FormData
        try {
            updatePostService.updateImages(postId, deletedImages, newImages);
            return ResponseEntity.ok(Collections.singletonMap("message", "Images updated successfully"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating images");
        }
    }

}
