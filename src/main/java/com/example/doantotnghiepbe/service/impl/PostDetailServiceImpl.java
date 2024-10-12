package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    private final Cloudinary cloudinary;
    @Autowired
    public PostDetailServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Autowired
    private PostDetailRepository postDetailRepository;


    @Override
    public Optional<Post> getPostById(Integer id) {
        return postDetailRepository.findById(id); // Gọi repository để tìm bài đăng theo ID
    }

    public String uploadImage(byte[] imageBytes) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();  // Get the URL of the uploaded image
    }
}
