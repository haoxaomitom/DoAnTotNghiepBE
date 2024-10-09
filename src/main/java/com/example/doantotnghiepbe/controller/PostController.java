package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PostController {

    @Autowired
    private PostService postService;

    // Lấy tất cả thông tin các post với phân trang
    @GetMapping

    public ResponseEntity<Page<Post>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Page<Post> postPage = postService.getAllPosts(PageRequest.of(page, size));
        return ResponseEntity.ok(postPage); // Lấy danh sách từ Page
    }

    @GetMapping("/countByDistrict")
    public ResponseEntity<Map<String, Integer>> countPostsByDistrict() {
        List<Object[]> results = postService.countPostsByDistrict();
        Map<String, Integer> districtCounts = new HashMap<>();

        for (Object[] result : results) {
            String districtName = (String) result[0];
            Long count = (Long) result[1];
            districtCounts.put(districtName, count.intValue());
        }

        return ResponseEntity.ok(districtCounts);
    }
}
