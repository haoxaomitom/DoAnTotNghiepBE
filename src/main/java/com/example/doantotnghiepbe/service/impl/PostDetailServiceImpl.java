package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.exception.ResourceNotFoundException;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Override
    public PostDetailDTO getPostDetails(Long id_post) {
        Post post = postDetailRepository.findByIdWithAmenities(id_post)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Chuyển đổi từ Post entity sang PostDetailDTO
        PostDetailDTO dto = new PostDetailDTO();
        dto.setId_post(post.getId_post());
        dto.setParking_name(post.getParking_name());
        dto.setPrice(post.getPrice());
        dto.setPrice_per(post.getPrice_per());
        dto.setCapacity(post.getCapacity());
        dto.setWard_name(post.getWard_name());
        dto.setDistrict_name(post.getDistrict_name());
        dto.setProvince_name(post.getProvince_name());
        dto.setLatitude(post.getLatitude());
        dto.setLongitude(post.getLongitude());
        dto.setImage(post.getImage());
        dto.setOpening_hours(post.getOpening_hours());
        dto.setStatus(post.getStatus());

        // Lấy danh sách tiện ích từ Post
        List<String> amenities = post.getAmenities()
                .stream()
                .map(amenity -> amenity.getAmenities_name())
                .collect(Collectors.toList());
        dto.setAmenities(amenities);

        return dto;
    }
}
