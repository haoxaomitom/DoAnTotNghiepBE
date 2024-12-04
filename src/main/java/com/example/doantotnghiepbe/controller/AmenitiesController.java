package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.AmenityDTO;
import com.example.doantotnghiepbe.service.AmenitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/amenities")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AmenitiesController {
    @Autowired
    private AmenitiesService amenitiesService;
    @PostMapping("/{postId}")
    public ResponseEntity<Map<String, String>> saveAmenities(
            @PathVariable Integer postId,
            @RequestBody List<AmenityDTO> amenitiesDTOs) {
        amenitiesService.saveAmenities(amenitiesDTOs, postId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Amenities saved successfully!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<AmenityDTO>> getAmenitiesByPostId(@PathVariable Integer postId) {
        List<AmenityDTO> amenities = amenitiesService.getAllAmenitiesByPostId(postId);
        return ResponseEntity.ok(amenities);
    }
}
