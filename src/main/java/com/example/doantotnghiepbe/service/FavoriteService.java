package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.dto.FavoritePostDTO;
import com.example.doantotnghiepbe.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite likePost(Integer userId, Integer postId);
    List<FavoritePostDTO> getFavoritesByUserUserId(Integer user);

    void unlikePost(Integer userId, Integer postId);

    boolean isFavorite(Integer userId, Integer postId);
    boolean toggleFavorite(Integer userId, Integer postId);
}
