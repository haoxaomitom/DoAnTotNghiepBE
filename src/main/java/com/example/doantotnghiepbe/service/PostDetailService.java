package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostDetailService {

    Optional<PostDetailDTO> getPostById(Integer id);
    String uploadImage(byte[] imageBytes) throws IOException;

    PostDetailDTO updatePost(PostDetailDTO postDetailDTO);

    void deletePostById(Integer id);

    Page<PostDTO> getRelatedPostsByDistrict(String districtName, Pageable pageable);


}
