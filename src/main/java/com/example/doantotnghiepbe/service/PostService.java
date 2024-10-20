package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<Object[]> countPostsByDistrict();

    Page<PostDTO> searchPosts(String searchTerm, int page);

    Page<PostDTO> getAllPosts(Pageable pageable);

    Page<PostDTO> searchPostsByVehicleType(String vehicleType, int page);

}
