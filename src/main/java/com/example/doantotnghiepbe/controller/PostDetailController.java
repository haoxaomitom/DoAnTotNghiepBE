package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostDetailController {

    @Autowired
    private PostDetailService postDetailService;

    @GetMapping("/{id_post}/details")
    public ResponseEntity<PostDetailDTO> getPostDetails(@PathVariable Long id_post) {
        PostDetailDTO postDetail = postDetailService.getPostDetails(id_post);
        return ResponseEntity.ok(postDetail);
    }

    // Lấy thông tin post theo id
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
//        return postService.getPostById(id)
//                .map(post -> ResponseEntity.ok(post))
//                .orElse(ResponseEntity.notFound().build());
//    }
}
