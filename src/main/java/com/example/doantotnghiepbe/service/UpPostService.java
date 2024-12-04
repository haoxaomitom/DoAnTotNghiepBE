package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.dto.UpPostDTO;
import com.example.doantotnghiepbe.entity.Post;

import java.util.List;

public interface UpPostService {
    List<UpPostDTO> getAllPosts();
    UpPostDTO getPostById(Integer id);
    UpPostDTO savePost(UpPostDTO upPostDTO);
    UpPostDTO updatePost(Integer id, UpPostDTO upPostDTO);
    void deletePost(Integer id);

    Post createPost(PostDetailDTO postDetailDTO);
}
