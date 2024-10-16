package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostDetailService {

    Optional<PostDetailDTO> getPostById(Integer id);
    String uploadImage(byte[] imageBytes) throws IOException;

    List<Post> getRelatedPostsByDistrict(String districtName);
}
