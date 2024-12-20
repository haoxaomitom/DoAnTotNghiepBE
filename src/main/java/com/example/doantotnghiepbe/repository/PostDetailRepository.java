package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostDetailRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByDistrictName(String districtName, Pageable pageable);
    Optional<Post> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
}
