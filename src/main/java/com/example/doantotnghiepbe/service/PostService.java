package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<Object[]> countPostsByDistrict();

    Page<PostDTO> searchPosts(String searchTerm, Pageable pageable);

    Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<PostDTO> searchPostsByVehicleType(String vehicleType, Pageable pageable);

    Page<PostDTO> sortPostsByPrice(String sort, Pageable pageable);
}
