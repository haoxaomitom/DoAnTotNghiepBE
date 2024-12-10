package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.ApprovalPost;

import java.util.List;
import java.util.Optional;

public interface ApprovalPostService {
    List<ApprovalPost> getAllApprovalPosts();
    Optional<ApprovalPost> getApprovalPostById(Integer id);
    ApprovalPost approvePost(Integer id, String reviewedByUsername);
    ApprovalPost rejectPost(Integer id, String rejectionReason, String reviewedByUsername);
}
