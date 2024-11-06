package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

//    @Override
//    public Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
//        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
//        return postPage.map(this::convertToDTO);
//    }

    @Override
    public Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<PostDTO> postDTOs = postPage.stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setCommentCount(post.getCommentCount());
                    return postDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }
    @Override
    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }

    @Override
    public Page<PostDTO> searchPosts(String searchTerm, Pageable pageable) {
        Page<Post> postPage = postRepository.searchPosts(searchTerm, pageable);
        return postPage.map(this::convertToDTO);
    }

    @Override
    public Page<PostDTO> searchPostsByVehicleType(String vehicleType, Pageable pageable) {
        Page<Post> postPage = postRepository.searchPostsByVehicleType(vehicleType, pageable);
        return postPage.map(this::convertToDTO);
    }

    @Override
    public Page<PostDTO> sortPostsByPrice(String sort, Pageable pageable) {
        Page<Post> postPage;
        if ("desc".equalsIgnoreCase(sort)) {
            postPage = postRepository.findAllByOrderByPriceDesc(pageable);
        } else {
            postPage = postRepository.findAllByOrderByPriceAsc(pageable);
        }
        return postPage.map(this::convertToDTO);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setCommentCount(post.getCommentCount());
        return postDTO;
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserUserId(userId);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .toList();
    }
}
