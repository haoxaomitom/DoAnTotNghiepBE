package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found");
        }
        post.setId_post(id);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found");
        }
        postRepository.deleteById(id);
    }
}
