package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "reporting_user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "post_reported_id")
    private Post post;

    @Column(name = "report_content")
    private String reportContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name ="report_type")
    private String reportType;

    @Column(name = "status")
    private String status;

    @Column(name = "rejected_reason")
    private String rejectedReason;

}
