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

    @GetMapping
    public ResponseEntity<Page<ApprovalPost>> getApprovalPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Page<ApprovalPost> approvalPosts = approvalPostService.getApprovalPostsSortedByWaitingAndDate(page, size);
        return ResponseEntity.ok(approvalPosts);
    }

    // Lấy tất cả bài viết với phân trang
//    @GetMapping
//    public ResponseEntity<Page<ApprovalPost>> getAllApprovalPosts(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size) {
//        Page<ApprovalPost> posts = approvalPostService.getAllApprovalPosts(page, size);
//        return ResponseEntity.ok(posts);
//    }

    // Duyệt bài viết
    @PostMapping("/approve/{postId}")
    public ResponseEntity<ApprovalPost> approvePost(
            @PathVariable("postId") Integer postId,
            @RequestParam("userId") Long userId) {
        ApprovalPost approvedPost = approvalPostService.approvePost(postId, userId);
        return ResponseEntity.ok(approvedPost);
    }


    // Từ chối bài viết
    @PostMapping("/reject/{postId}")
    public ResponseEntity<ApprovalPost> rejectPost(
            @PathVariable("postId") Integer postId,
            @RequestParam("rejectionReason") String rejectionReason,
            @RequestParam("userId") Long userId) {
        ApprovalPost rejectedPost = approvalPostService.rejectPost(postId, rejectionReason, userId);
        return ResponseEntity.ok(rejectedPost);
    }


    @GetMapping("/search")
    public Page<ApprovalPost> searchApprovalPosts(
            @RequestParam String postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Page<ApprovalPost> searchPost = approvalPostService.searchApprovalPostsByPostId(postId, page,size);
        return ResponseEntity.ok(searchPost).getBody();

    }

}
