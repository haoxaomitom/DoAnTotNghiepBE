package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PostDetailController {

    @Autowired
    private PostDetailService postDetailService;

    // Lấy thông tin post theo id
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        return postDetailService.getPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElse(ResponseEntity.notFound().build());
    }
}
