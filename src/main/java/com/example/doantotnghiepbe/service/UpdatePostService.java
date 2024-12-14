package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.dto.UpdatePostDTO;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdatePostService {


    Post updatePost(Integer postId, UpdatePostDTO postRequest);


    List<String> uploadImages(Integer postId, List<MultipartFile> imageFiles);
}
