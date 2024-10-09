package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable); // Gọi repository để lấy danh sách bài đăng với phân trang
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id); // Gọi repository để tìm bài đăng theo ID
    }

    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }
}
