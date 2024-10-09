package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p.district_name, COUNT(p) FROM Post p GROUP BY p.district_name")
    List<Object[]> countPostsByDistrict();
}

