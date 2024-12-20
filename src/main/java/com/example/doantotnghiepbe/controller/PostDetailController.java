package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PostDetailController {

    @Autowired
    private PostDetailService postDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPostDetails(@PathVariable Integer id) {
        Optional<PostDetailDTO> postDetailDTO = postDetailService.getPostById(id);
        return postDetailDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // API để lấy các bài đăng liên quan theo quận
    @GetMapping("/related")
    public ResponseEntity<Page<PostDTO>> getRelatedPostsByDistrict(@RequestParam String districtName, Pageable pageable) {
        Page<PostDTO> relatedPosts = postDetailService.getRelatedPostsByDistrict(districtName, pageable);
        return ResponseEntity.ok(relatedPosts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDetailDTO> updatePost(@PathVariable Integer id, @RequestBody PostDetailDTO postDetailDTO) {
        // Kiểm tra ID khớp nhau
        if (!id.equals(postDetailDTO.getPostId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            PostDetailDTO updatedPost = postDetailService.updatePost(postDetailDTO);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        try {
            postDetailService.deletePostById(postId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
