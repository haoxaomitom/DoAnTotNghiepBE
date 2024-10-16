package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostDetailServiceImpl implements PostDetailService {

    private final Cloudinary cloudinary;
    private final PostDetailRepository postDetailRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PostDetailServiceImpl(Cloudinary cloudinary, PostDetailRepository postDetailRepository, ModelMapper modelMapper) {
        this.cloudinary = cloudinary;
        this.postDetailRepository = postDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<PostDetailDTO> getPostById(Integer id) {
        Optional<Post> postOptional = postDetailRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Convert the Post entity to PostDetailDTO using ModelMapper
            PostDetailDTO postDetailDTO = modelMapper.map(post, PostDetailDTO.class);

            // Manually set user details in PostDetailDTO (First name, Last name, Phone, etc.)
            postDetailDTO.getUser().setFirstName(post.getUser().getFirstName());
            postDetailDTO.getUser().setLastName(post.getUser().getLastName());
            postDetailDTO.getUser().setPhoneNumber(post.getUser().getPhoneNumber());
            postDetailDTO.getUser().setCreatedAt(post.getUser().getCreatedAt());
            postDetailDTO.getUser().setAvatar(post.getUser().getAvatar());
            return Optional.of(postDetailDTO);
        }
        return Optional.empty();
    }

    @Override
    public String uploadImage(byte[] imageBytes) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();  // Get the URL of the uploaded image
    }

    @Override
    public List<Post> getRelatedPostsByDistrict(String districtName) {
        return postDetailRepository.findByDistrictName(districtName);
    }
}
