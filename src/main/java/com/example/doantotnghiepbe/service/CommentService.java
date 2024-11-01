package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);

    Page<CommentDTO> getCommentsByPostId(Integer postId, Pageable pageable);

    void deleteComment(Integer commentId, Integer userId);  // New method
}
