package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.AmenitiesDTO;
import com.example.doantotnghiepbe.dto.ImageDTO;
import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.VehicleTypeDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository; // Giả sử bạn đã có PostRepository
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    // Phương thức để lưu một Post và trả về PostDTO
    public PostDTO createPost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostDTO> postDTOs = postPage.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }

    // Phương thức để tìm Post theo ID
    public PostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(post, PostDTO.class);
    }

    // Phương thức để cập nhật một Post
    public PostDTO updatePost(Integer id, PostDTO postDTO) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        modelMapper.map(postDTO, existingPost); // Ánh xạ các trường từ postDTO vào existingPost
        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    // Phương thức để xóa Post
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }


    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }

    public Page<Post> searchPosts(String searchTerm, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return postRepository.searchPosts(searchTerm, pageable);
    }

}

