package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.AmenitiesDTO;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.dto.UpPostDTO;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpPostService {
    List<UpPostDTO> getAllPosts();
    UpPostDTO getPostById(Integer id);
    UpPostDTO savePost(UpPostDTO upPostDTO);
    UpPostDTO updatePost(Integer id, UpPostDTO upPostDTO);
    void deletePost(Integer id);


    Post createPost(PostDetailDTO postRequest);

    List<Image> uploadImages(Integer postId, List<MultipartFile> imageFiles);

}
