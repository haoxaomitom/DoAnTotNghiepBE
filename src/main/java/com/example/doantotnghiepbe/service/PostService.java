package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.PostDetailDTO;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<Object[]> countPostsByDistrict();

    Page<PostDTO> searchPosts(String searchTerm, Pageable pageable);

    Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<PostDTO> searchPostsByVehicleType(String vehicleType, Pageable pageable);

    Page<PostDTO> sortPostsByPrice(String sort, Pageable pageable);
    


    Page<PostDTO> findAllTopPostsOrderByPaymentAndDate(Pageable pageable);

    void softDeletePost(Integer postId);

    List<PostDTO> getPostsByUserId(Long userId);


    Page<Post> getPostsByUserIdAndStatus(Long userId, String status, int page, int size);
}
