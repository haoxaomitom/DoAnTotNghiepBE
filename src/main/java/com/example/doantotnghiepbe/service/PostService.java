package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Page<Post> getAllPosts(Pageable pageable);
    Optional<Post> getPostById(Long id);

    List<Object[]> countPostsByDistrict();
}
