package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Favorite;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.repository.FavoriteRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.FavoriteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {


    private final FavoriteRepository favoriteRepository;


    private final UsersRepository userRepository;


    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UsersRepository userRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Favorite likePost(Long userId, Integer postId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (favoriteRepository.existsByUserUserIdAndPostPostId(userId, postId)) {
            throw new IllegalStateException("Post already liked by the user");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setPost(post);

        return favoriteRepository.save(favorite);
    }

    public Page<PostDTO> getFavoritePostsByUserId(Long userId, Pageable pageable) {
        Page<Favorite> favorites = favoriteRepository.getFavoritesByUserUserId(userId, pageable);

        // Map each `Favorite`'s `Post` to `PostDTO`
        return favorites.map(favorite -> modelMapper.map(favorite.getPost(), PostDTO.class));
    }

    @Override
    public void unlikePost(Long userId, Integer postId) {
        favoriteRepository.findByUserUserIdAndPostPostId(userId, postId)
                .ifPresentOrElse(favoriteRepository::delete,
                        () -> { throw new IllegalStateException("Favorite not found."); });
    }

    public boolean toggleFavorite(Long userId, Integer postId) {
        Optional<Favorite> existingFavorite = favoriteRepository.findByUserUserIdAndPostPostId(userId, postId);

        if (existingFavorite.isPresent()) {
            // If the favorite already exists, delete it (unlike)
            favoriteRepository.delete(existingFavorite.get());
            return false;
        } else {
            // Fetch User and Post entities to ensure non-null references
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("Post not found"));

            // Create a new favorite with references to both User and Post
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setPost(post);

            favoriteRepository.save(favorite); // Save the new favorite
            return true;
        }
    }

    public boolean isFavorite(Long userId, Integer postId) {
        return favoriteRepository.existsByUserUserIdAndPostPostId(userId, postId);
    }

}
