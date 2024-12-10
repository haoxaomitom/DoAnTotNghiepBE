package com.example.doantotnghiepbe.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalPostDTO {
    private Integer approvalPostId;
    private Integer postId; // Only the ID of the post
    private String status;
    private String rejectionReason;
    private LocalDateTime reviewedAt;
    private String reviewedByUsername; // Reviewed user's username
}

