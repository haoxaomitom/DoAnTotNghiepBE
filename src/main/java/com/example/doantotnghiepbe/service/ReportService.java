package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ReportDTO;
import com.example.doantotnghiepbe.entity.Report;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO);

    List<ReportDTO> getAllReports();

    ReportDTO getReportById(int id);
    // Phương thức mới để cập nhật trạng thái báo cáo
    ReportDTO updateReportStatus(int reportId, String status);

    // Phương thức tìm kiếm báo cáo theo trạng thái
    List<ReportDTO> getReportsByStatus(String status);
    // Các phương thức mới cho tìm kiếm và lọc báo cáo

    List<ReportDTO> searchReports(String status, String reportType, String reportContent);
    List<ReportDTO> filterReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}