package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findByPostPostId(Integer postId, Pageable pageable);
}
