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

    @Override
    public Page<PostDTO> sortPostsByPrice(String sort, Pageable pageable) {
        if ("desc".equalsIgnoreCase(sort)) {
            return postRepository.findAllByOrderByPriceDesc(pageable)
                    .map(this::convertToDTO);
        } else { // Default to ascending
            return postRepository.findAllByOrderByPriceAsc(pageable)
                    .map(this::convertToDTO);
        }
    }


    private PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}


