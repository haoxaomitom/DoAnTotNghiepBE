package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteService {
    Favorite likePost(Long userId, Integer postId);


    void unlikePost(Long userId, Integer postId);

    boolean isFavorite(Long userId, Integer postId);
    boolean toggleFavorite(Long userId, Integer postId);

    Page<PostDTO> getFavoritePostsByUserId(Long userId, Pageable pageable);
}
