package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://127.0.0.1")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> findAllByOrderByCreatedAtDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> posts = postService.findAllByOrderByCreatedAtDesc(pageable);
        return ResponseEntity.ok(posts);
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

    @GetMapping("/search")
    public ResponseEntity<Page<PostDTO>> searchPosts(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> posts = postService.searchPosts(searchTerm, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/searchVehicleType")
    public ResponseEntity<Page<PostDTO>> searchPostsByVehicleType(
            @RequestParam String vehicleType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> posts = postService.searchPostsByVehicleType(vehicleType, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Method for sorting posts by price
    @GetMapping("/sort")
    public ResponseEntity<Page<PostDTO>> sortPostsByPrice(
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Xác định hướng sắp xếp
        Sort.Direction sortDirection = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Tạo Pageable với điều kiện sắp xếp theo giá
        Pageable pageable = PageRequest.of(page, size);

        // Gọi service để lấy danh sách bài đăng đã được sắp xếp
        Page<PostDTO> posts = postService.sortPostsByPrice(sort, pageable);

        // Trả về danh sách bài đăng đã sắp xếp
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDTO>> getPostsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<PostDTO> posts = postService.getPostsByUserId(userId, page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/top")
    public ResponseEntity<Page<PostDTO>> findAllTopPostsOrderByPaymentAndDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> topPosts = postService.findAllTopPostsOrderByPaymentAndDate(pageable);
        return ResponseEntity.ok(topPosts);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> softDeletePost(@PathVariable Integer postId) {
        postService.softDeletePost(postId);
        return ResponseEntity.ok("Post successfully soft deleted.");
    }

}
