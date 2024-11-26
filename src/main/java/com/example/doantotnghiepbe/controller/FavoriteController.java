package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ApiResponse;
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

//    @PostMapping("/like")
//    public ResponseEntity<ApiResponse<String>> likePost(@RequestParam Long userId, @RequestParam Integer postId) {
//        Favorite favorite = favoriteService.likePost(userId, postId);
//        ApiResponse<String> response = new ApiResponse<>("success", "Like successfully", null);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FavoritePostDTO>>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoritePostDTO> favorites = favoriteService.getFavoritesByUserUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Favorites retrieved successfully", favorites));
    }

//    @DeleteMapping("/unlike")
//    public ResponseEntity<ApiResponse<String>> unlikePost(@RequestParam Long userId, @RequestParam Integer postId) {
//        favoriteService.unlikePost(userId, postId);
//        ApiResponse<String> response = new ApiResponse<>("success", "Unliked successfully", null);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse<Map<String, Object>>> toggleFavorite(
            @RequestParam Long userId,
            @RequestParam Integer postId) {

        boolean isFavorited = favoriteService.toggleFavorite(userId, postId);
        Map<String, Object> response = Map.of("isFavorite", isFavorited);
        return ResponseEntity.ok(new ApiResponse<>("success", "Favorite status toggled successfully", response));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> checkFavorite(
            @RequestParam Long userId,
            @RequestParam Integer postId) {

        boolean isFavorite = favoriteService.isFavorite(userId, postId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Favorite status checked successfully", isFavorite));
    }
}


