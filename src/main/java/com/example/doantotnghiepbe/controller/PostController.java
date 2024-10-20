package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> posts = postService.getAllPosts(pageable);
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
    public ResponseEntity<Page<PostDTO>> searchPosts(@RequestParam String searchTerm,
                                                   @RequestParam(defaultValue = "0") int page) {
        Page<PostDTO> posts = postService.searchPosts(searchTerm, page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/searchVehicleType")
    public ResponseEntity<Page<PostDTO>> searchPostsByVehicleType(@RequestParam String vehicleType,
                                                                  @RequestParam(defaultValue = "0") int page){
        Page<PostDTO> posts = postService.searchPostsByVehicleType(vehicleType, page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
