package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ApiResponse; // Import your new ApiResponse class
import com.example.doantotnghiepbe.dto.FavoriteDTO;
import com.example.doantotnghiepbe.dto.FavoritePostDTO;
import com.example.doantotnghiepbe.entity.Favorite;
import com.example.doantotnghiepbe.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<String>> likePost(@RequestParam Integer userId, @RequestParam Integer postId) {
        Favorite favorite = favoriteService.likePost(userId, postId);
        ApiResponse<String> response = new ApiResponse<>("success", "Like successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FavoritePostDTO>>> getFavoritesByUser(@PathVariable Integer userId) {
        List<FavoritePostDTO> favorites = favoriteService.getFavoritesByUserUserId(userId);
        ApiResponse<List<FavoritePostDTO>> response = new ApiResponse<>("success", "Favorites retrieved successfully", favorites);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<ApiResponse<String>> unlikePost(@RequestParam Integer userId, @RequestParam Integer postId) {
        favoriteService.unlikePost(userId, postId);
        ApiResponse<String> response = new ApiResponse<>("success", "Unliked successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleFavorite(
            @RequestParam Integer userId,
            @RequestParam Integer postId) {

        boolean isFavorited = favoriteService.toggleFavorite(userId, postId);

        Map<String, Object> response = new HashMap<>();
        response.put("isFavorite", isFavorited);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFavorite(@RequestParam Integer userId, @RequestParam Integer postId) {
        boolean isFavorite = favoriteService.isFavorite(userId, postId);
        return ResponseEntity.ok(isFavorite);
    }
}

