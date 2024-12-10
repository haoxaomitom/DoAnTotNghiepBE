package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ApprovalPostDTO;
import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.service.ApprovalPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-posts")
@CrossOrigin(origins = "http://127.0.0.1:5500/") // Allow CORS for all origins
public class ApprovalPostController {

    @Autowired
    private ApprovalPostService approvalPostService;

    @GetMapping
    public ResponseEntity<List<ApprovalPostDTO>> getAllApprovalPosts() {
        List<ApprovalPostDTO> approvalPosts = approvalPostService.getAllApprovalPosts().stream().map(post -> {
            ApprovalPostDTO dto = new ApprovalPostDTO();
            dto.setApprovalPostId(post.getApprovalPostId());
            dto.setPostId(post.getPost().getPostId());
            dto.setStatus(post.getStatus());
            dto.setRejectionReason(post.getRejectionReason());
            dto.setReviewedAt(post.getReviewedAt());
            dto.setReviewedByUsername(post.getReviewedByUser() != null ? post.getReviewedByUser().getUsername() : null);
            return dto;
        }).toList();

        return ResponseEntity.ok(approvalPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalPostDTO> getApprovalPostById(@PathVariable Integer id) {
        Optional<ApprovalPost> approvalPostOpt = approvalPostService.getApprovalPostById(id);
        if (approvalPostOpt.isPresent()) {
            ApprovalPost post = approvalPostOpt.get();
            ApprovalPostDTO dto = new ApprovalPostDTO();
            dto.setApprovalPostId(post.getApprovalPostId());
            dto.setPostId(post.getPost().getPostId());
            dto.setStatus(post.getStatus());
            dto.setRejectionReason(post.getRejectionReason());
            dto.setReviewedAt(post.getReviewedAt());
            dto.setReviewedByUsername(post.getReviewedByUser() != null ? post.getReviewedByUser().getUsername() : null);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approvePost(@PathVariable Integer id, @RequestBody String reviewedByUsername) {
        try {
            ApprovalPost approvalPost = approvalPostService.approvePost(id, reviewedByUsername);
            return ResponseEntity.ok(approvalPost);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectPost(@PathVariable Integer id, @RequestBody String rejectionReason, @RequestBody String reviewedByUsername ) {
        try {
            ApprovalPost approvalPost = approvalPostService.rejectPost(id, rejectionReason,reviewedByUsername);
            return ResponseEntity.ok(approvalPost);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteApprovalPost(@PathVariable Integer id) {
//        try {
//            approvalPostService.deleteApprovalPost(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}
