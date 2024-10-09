package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostDetailRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.amenities WHERE p.id_post = :id")
    Optional<Post> findByIdWithAmenities(@Param("id") Long id_post);
}
