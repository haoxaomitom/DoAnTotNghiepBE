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

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostDTO> postDTOs = postPage.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))  // Convert each Post entity to PostDTO
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }

//    @Override
//    public PostDTO getPostById(Integer id) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        return modelMapper.map(post, PostDTO.class);  // Map the entity to DTO
//    }

//    @Override
//    public PostDTO createPost(PostDTO postDTO) {
//        Post post = modelMapper.map(postDTO, Post.class);  // Convert DTO to entity
//        Post savedPost = postRepository.save(post);        // Save the entity
//        return modelMapper.map(savedPost, PostDTO.class);  // Convert entity back to DTO
//    }
//
//    @Override
//    public PostDTO updatePost(Integer id, PostDTO postDTO) {
//        Post existingPost = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        modelMapper.map(postDTO, existingPost);  // Map DTO fields to the existing entity
//        Post updatedPost = postRepository.save(existingPost);
//        return modelMapper.map(updatedPost, PostDTO.class);  // Return the updated DTO
//    }
//
//    @Override
//    public void deletePost(Integer id) {
//        postRepository.deleteById(id);
//    }

    @Override
    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }

    @Override
    public Page<PostDTO> searchPosts(String searchTerm, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return postRepository.searchPosts(searchTerm, pageable)
                .map(post -> modelMapper.map(post, PostDTO.class));  // Map entities to DTOs
    }

    @Override
    public Page<PostDTO> searchPostsByVehicleType(String vehicleType, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return postRepository.searchPostsByVehicleType(vehicleType, pageable)
                .map(post -> modelMapper.map(post, PostDTO.class));  // Map entities to DTOs
    }
}


