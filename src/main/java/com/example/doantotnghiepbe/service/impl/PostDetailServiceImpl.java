package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.dto.PostUserDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostDetailRepository;
import com.example.doantotnghiepbe.service.PostDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PostDetailDTO updatePost(PostDetailDTO postDetailDTO) {
        // Kiểm tra xem bài đăng có tồn tại hay không
        Optional<Post> existingPostOptional = postDetailRepository.findById(postDetailDTO.getIdPost());
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            // Cập nhật thông tin bài đăng
            existingPost.setParkingName(postDetailDTO.getParkingName());
            existingPost.setPrice(postDetailDTO.getPrice());
            existingPost.setPriceUnit(postDetailDTO.getPriceUnit());
            existingPost.setCapacity(postDetailDTO.getCapacity());
            existingPost.setWardName(postDetailDTO.getWardName());
            existingPost.setDistrictName(postDetailDTO.getDistrictName());
            existingPost.setProvinceName(postDetailDTO.getProvinceName());
            existingPost.setLatitude(postDetailDTO.getLatitude());
            existingPost.setLongitude(postDetailDTO.getLongitude());
            existingPost.setStatus(postDetailDTO.getStatus());
            existingPost.setDescription(postDetailDTO.getDescription());

            // Lưu thay đổi vào cơ sở dữ liệu
            Post updatedPost = postDetailRepository.save(existingPost);

            // Sử dụng ModelMapper để chuyển đổi từ Entity sang DTO
            return modelMapper.map(updatedPost, PostDetailDTO.class);
        } else {
            throw new IllegalArgumentException("Post not found with id: " + postDetailDTO.getIdPost());
        }
    }


    @Override
    public void deletePostById(Integer id) {
        // Kiểm tra xem bài đăng có tồn tại không
        if (postDetailRepository.existsById(id)) {
            postDetailRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
    }


    @Override
    public Page<PostDTO> getRelatedPostsByDistrict(String districtName, Pageable pageable) {
        Page<Post> postPage = postDetailRepository.findByDistrictName(districtName, pageable);
        List<PostDTO> postDTOs = postPage.stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setCommentCount(post.getCommentCount());

                    if (post.getUser() != null) {
                        PostUserDTO userDTO = new PostUserDTO(post.getUser().getFirstName(), post.getUser().getLastName());
                        postDTO.setUser(userDTO);
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }




//    @Override
//    public List<Post> getRelatedPostsByDistrict(String districtName) {
//        return postDetailRepository.findByDistrictName(districtName);
//    }
}
