package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;

import java.util.Optional;

public interface PostDetailService {

    Optional<Post> getPostById(Integer id);
}
