package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.service.ApprovalPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approval-posts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ApprovalPostController {

    @Autowired
    private ApprovalPostService approvalPostService;

    // Lấy tất cả bài viết với phân trang
    @GetMapping
    public ResponseEntity<Page<ApprovalPost>> getAllApprovalPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ApprovalPost> posts = approvalPostService.getAllApprovalPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    // Duyệt bài viết
    @PostMapping("/approve/{id}")
    public ResponseEntity<ApprovalPost> approvePost(
            @PathVariable("id") Integer id,
            @RequestParam("userId") Long userId) {
        ApprovalPost approvedPost = approvalPostService.approvePost(id, userId);
        return ResponseEntity.ok(approvedPost);
    }

    // Từ chối bài viết
    @PostMapping("/reject/{id}")
    public ResponseEntity<ApprovalPost> rejectPost(
            @PathVariable("id") Integer id,
            @RequestParam("rejectionReason") String rejectionReason,
            @RequestParam("userId") Long userId) {
        ApprovalPost rejectedPost = approvalPostService.rejectPost(id, rejectionReason, userId);
        return ResponseEntity.ok(rejectedPost);
    }
}
