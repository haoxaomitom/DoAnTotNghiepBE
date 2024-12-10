package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.AmenityDTO;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.AmenitiesRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.AmenitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmenitiesServiceImpl implements AmenitiesService {
    @Autowired
    private AmenitiesRepository amenitiesRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public void saveAmenities(List<AmenityDTO> amenities, Integer postId) {
    // Tìm Post theo postId
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Chuyển DTO sang entity và lưu vào cơ sở dữ liệu
        List<Amenities> amenitiesList = new ArrayList<>();
        for (AmenityDTO amenityDTO : amenities) {
            Amenities amenity = new Amenities();
            amenity.setAmenitiesName(amenityDTO.getAmenitiesName());
            amenity.setPost(post);
            amenitiesList.add(amenity);
        }

        amenitiesRepository.saveAll(amenitiesList);
    }

    @Override
    public List<AmenityDTO> getAllAmenitiesByPostId(Integer postId) {
        // Lấy danh sách tiện ích theo postId
        List<Amenities> amenitiesList = amenitiesRepository.findAll()
                .stream()
                .filter(amenity -> amenity.getPost().getPostId().equals(postId))
                .toList();

        // Chuyển đổi từ entity sang DTO
        List<AmenityDTO> amenitiesDTOList = new ArrayList<>();
        for (Amenities amenity : amenitiesList) {
            AmenityDTO dto = new AmenityDTO();
            dto.setAmenitiesId(amenity.getAmenitiesId());
            dto.setAmenitiesName(amenity.getAmenitiesName());
            amenitiesDTOList.add(dto);
        }

        return amenitiesDTOList;

    }
}
