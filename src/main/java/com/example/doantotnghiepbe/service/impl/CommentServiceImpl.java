package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.CommentDTO;
import com.example.doantotnghiepbe.dto.CommentUserDTO;
import com.example.doantotnghiepbe.entity.Comment;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.repository.CommentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UsersRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        // Map CommentDTO to Comment entity
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setCreatedAt(LocalDateTime.now());

        // Fetch and set the user based on userId
        Users user = userRepository.findById(commentDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        // Fetch and set the post based on postId
        Post post = postRepository.findById(commentDTO.getPost())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);

        // Save the comment
        comment = commentRepository.save(comment);

        // Increment comment count for the post and save it
        post.setCommentCount((post.getCommentCount() == null ? 0 : post.getCommentCount()) + 1);
        postRepository.save(post);

        return convertToDto(comment);
    }


    @Override
    public Page<CommentDTO> getCommentsByPostId(Integer postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostPostId(postId, pageable);
        return comments.map(this::convertToDto);
    }

    @Override
    public void deleteComment(Integer commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Check if the user is the owner of the comment
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }

        // Get the associated post
        Post post = comment.getPost();

        // Decrement the comment count for the post
        post.setCommentCount((post.getCommentCount() == null ? 0 : post.getCommentCount()) - 1);

        postRepository.save(post);
        commentRepository.delete(comment);
    }
    
    private CommentDTO convertToDto(Comment comment) {
        // Convert Comment to CommentDTO
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        // Populate cud list with user data
        Users user = comment.getUser();
        CommentUserDTO commentUserDTO = new CommentUserDTO(
                user.getAvatar(),
                user.getFirstName(),
                user.getLastName()
        );

        commentDTO.setCud(List.of(commentUserDTO));
        return commentDTO;
    }
}
