package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.FavoritePostDTO;
import com.example.doantotnghiepbe.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite likePost(Long userId, Integer postId);
    List<FavoritePostDTO> getFavoritesByUserUserId(Long user);

    void unlikePost(Long userId, Integer postId);

    boolean isFavorite(Long userId, Integer postId);
    boolean toggleFavorite(Long userId, Integer postId);
}
