package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p.district_name, COUNT(p) FROM Post p GROUP BY p.district_name")
    List<Object[]> countPostsByDistrict();

    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.parking_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.ward_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.district_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.province_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);
}

