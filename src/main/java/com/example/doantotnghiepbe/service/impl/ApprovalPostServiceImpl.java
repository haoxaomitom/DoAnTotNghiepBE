package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.repository.ApprovalPostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.ApprovalPostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ApprovalPostServiceImpl implements ApprovalPostService {

    @Autowired
    private ApprovalPostRepository approvalPostRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Lấy tất cả bài viết với phân trang
    public Page<ApprovalPost> getAllApprovalPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return approvalPostRepository.findAll(pageable);
    }


    // Duyệt bài viết
    @Transactional
    public ApprovalPost approvePost(Integer approvalPostId, Long userId) {
        Optional<Users> reviewer = usersRepository.findById(userId);
        if (reviewer.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        Optional<ApprovalPost> optionalApprovalPost = approvalPostRepository.findById(approvalPostId);
        if (optionalApprovalPost.isEmpty()) {
            throw new RuntimeException("Approval Post not found with ID: " + approvalPostId);
        }

        ApprovalPost approvalPost = optionalApprovalPost.get();
        approvalPost.setStatus("ACTIVE");
        approvalPost.setReviewedAt(LocalDateTime.now());
        approvalPost.setReviewedByUser(reviewer.get());

        // Cập nhật trạng thái của Post liên kết
        Post post = approvalPost.getPost();
        post.setStatus("ACTIVE");

        return approvalPostRepository.save(approvalPost);
    }

    // Từ chối bài viết
    @Transactional
    public ApprovalPost rejectPost(Integer approvalPostId, String rejectionReason, Long userId) {
        Optional<Users> reviewer = usersRepository.findById(userId);
        if (reviewer.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        Optional<ApprovalPost> optionalApprovalPost = approvalPostRepository.findById(approvalPostId);
        if (optionalApprovalPost.isEmpty()) {
            throw new RuntimeException("Approval Post not found with ID: " + approvalPostId);
        }

        ApprovalPost approvalPost = optionalApprovalPost.get();
        approvalPost.setStatus("REJECT"); // Chuyển trạng thái thành REJECT
        approvalPost.setRejectionReason(rejectionReason);
        approvalPost.setReviewedAt(LocalDateTime.now());
        approvalPost.setReviewedByUser(reviewer.get());

        Post post = approvalPost.getPost();
        post.setStatus("REJECT");

        return approvalPostRepository.save(approvalPost);
    }

    @Override
    public Page<ApprovalPost> searchByApprovalPostIdLike(String approvalPostId, Pageable pageable) {
        return approvalPostRepository.searchByApprovalPostIdLike(approvalPostId, pageable);
    }

}
