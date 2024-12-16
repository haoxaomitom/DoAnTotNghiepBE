package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByImageUrlAndPost(String url, Post post);

    List<Image> findByPost(Post post);

    Image findByImageUrl(String url);

    void deleteByImageUrl(String imageUrl);
}

