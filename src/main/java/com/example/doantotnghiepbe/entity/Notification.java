package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users user; // Can be null if it's a global notification

    @Column(name = "title")
    private String title;

    @Column(name = "notification_content", nullable = false)
    private String content;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "is_global", nullable = false)
    private Boolean isGlobal = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
