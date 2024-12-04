package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Integer reportId;
    private Long user;
    private Integer post;
    private String reportType;
    private String reportContent;
    private LocalDateTime createdAt;
    private String status;
}