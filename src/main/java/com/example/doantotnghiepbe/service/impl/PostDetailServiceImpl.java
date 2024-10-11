package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.VehicleType;
import com.example.doantotnghiepbe.exception.ResourceNotFoundException;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostDetailService;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    @Autowired
    private PostDetailRepository postDetailRepository;


    @Override
    public Optional<Post> getPostById(Integer id) {
        return postDetailRepository.findById(id); // Gọi repository để tìm bài đăng theo ID
    }

    //    @Override
//    public PostDTO getPostDetails(Integer id_post) {
//        Post post = postRepository.findByIdWithAmenities(id_post)
//                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
//
//        // Chuyển đổi từ Post entity sang PostDTO
//        PostDTO dto = new PostDTO();
//        dto.setId_post(post.getId_post());
//        dto.setParking_name(post.getParking_name());
//        dto.setPrice(post.getPrice());
//        dto.setPrice_per(post.getPrice_per());
//        dto.setCapacity(post.getCapacity());
//        dto.setWard_name(post.getWard_name());
//        dto.setDistrict_name(post.getDistrict_name());
//        dto.setProvince_name(post.getProvince_name());
//        dto.setLatitude(post.getLatitude());
//        dto.setLongitude(post.getLongitude());
//        dto.setImage(post.getImage());
//        dto.setOpening_hours(post.getOpening_hours());
//        dto.setStatus(post.getStatus());
//
//        // Lấy danh sách tiện ích từ Post
//        List<String> amenities = post.getAmenities()
//                .stream()
//                .map(Amenities::getAmenities_name)
//                .collect(Collectors.toList());
//        dto.setAmenities(amenities);
//
//        // Lấy danh sách loại phương tiện từ Post
//        List<String> vehicleTypes = post.getVehicleTypes()
//                .stream()
//                .map(VehicleType::getVehicleTypeName)
//                .collect(Collectors.toList());
//        dto.setVehicleTypes(vehicleTypes); // Set the vehicle types in the DTO
//
//        return dto;
//    }
}
