package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Post>> getRelatedPosts(@RequestParam String districtName) {
        List<Post> relatedPosts = postDetailService.getRelatedPostsByDistrict(districtName);
        return ResponseEntity.ok(relatedPosts);
    }
}
