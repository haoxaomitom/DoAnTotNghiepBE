package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.service.ApprovalPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approval-posts")
@CrossOrigin(origins = "http://127.0.0.1")
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

    @GetMapping("/search")
    public ResponseEntity<Page<ApprovalPost>> searchApprovalPosts(
            @RequestParam(required = true) String approvalPostId, // approvalPostId là String
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Gọi phương thức tìm kiếm với approvalPostId là String
        Page<ApprovalPost> results = approvalPostService.searchByApprovalPostIdLike(approvalPostId, pageable);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }

}
