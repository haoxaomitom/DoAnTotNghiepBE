package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "approval_posts")
public class ApprovalPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer approvalPostId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by_user_id")
    private Users reviewedByUser;

    // Enum for ApprovalPost Status
    public enum ApprovalPostStatus {
        APPROVED, REJECTED
    }
}
