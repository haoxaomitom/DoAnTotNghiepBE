package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.AmenityDTO;

import java.util.List;

public interface AmenitiesService {
    void saveAmenities(List<AmenityDTO> amenities, Integer postId);
    List<AmenityDTO> getAllAmenitiesByPostId(Integer postId);
}
