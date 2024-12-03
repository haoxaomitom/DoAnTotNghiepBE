package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByStatus(String status); // Phương thức tìm theo trạng thái
    // Tìm báo cáo theo trạng thái và loại báo cáo
    @Query("SELECT r FROM Report r WHERE (:status IS NULL OR r.status = :status) AND (:reportType IS NULL OR r.reportType LIKE %:reportType%) AND (:reportContent IS NULL OR r.reportContent LIKE %:reportContent%)")
    List<Report> findReports(@Param("status") String status,
                             @Param("reportType") String reportType,
                             @Param("reportContent") String reportContent);

    // Tìm báo cáo theo thời gian tạo (từ ngày đến ngày)
    @Query("SELECT r FROM Report r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Report> findReportsByDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
