package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.VehicleType;
import com.example.doantotnghiepbe.exception.ResourceNotFoundException;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable); // Gọi repository để lấy danh sách bài đăng với phân trang
    }


    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }


    public Page<Post> searchPosts(String searchTerm, int page) {
        Pageable pageable = PageRequest.of(page, 5); // 5 items per page
        return postRepository.searchPosts(searchTerm, pageable);
    }
}
