package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private boolean isGlobal;
    private Long userId;
    private String title;
    private String content;
    private boolean isRead = false;
    private LocalDateTime createAt = LocalDateTime.now();
}
