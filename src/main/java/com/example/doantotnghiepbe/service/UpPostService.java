package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.UpPostDTO;

import java.util.List;

public interface UpPostService {
    List<UpPostDTO> getAllPosts();
    UpPostDTO getPostById(Integer id);
    UpPostDTO savePost(UpPostDTO upPostDTO);
    UpPostDTO updatePost(Integer id, UpPostDTO upPostDTO);
    void deletePost(Integer id);
}
