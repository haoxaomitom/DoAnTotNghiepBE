package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApprovalPostService {

//    Page<ApprovalPost> getAllApprovalPosts(int page, int sizeint page, int size);

    ApprovalPost approvePost(Integer id, Long userId);

    ApprovalPost rejectPost(Integer id, String rejectionReason, Long userId);

    Page<ApprovalPost> searchByApprovalPostIdLike(String approvalPostId, Pageable pageable);

    Page<ApprovalPost> getApprovalPostsSortedByWaitingAndDate(int page, int size);
}
