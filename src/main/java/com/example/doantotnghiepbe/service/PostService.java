package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Page<Post> getAllPosts(Pageable pageable);

    List<Object[]> countPostsByDistrict();

    Page<Post> searchPosts(String searchTerm, int page);
}
