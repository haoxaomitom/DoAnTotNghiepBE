package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ReportDTO;
import com.example.doantotnghiepbe.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")

public class ReportAdminController {
    @Autowired
    private ReportService reportService;

    // Lấy tất cả báo cáo
    @GetMapping("all")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reportsDTO = reportService.getAllReports();
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    // Lọc báo cáo theo trạng thái, loại báo cáo, nội dung
    @GetMapping("/search")
    public ResponseEntity<List<ReportDTO>> searchReports(@RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String reportType,
                                                         @RequestParam(required = false) String reportContent) {
        List<ReportDTO> reportsDTO = reportService.searchReports(status, reportType, reportContent);
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    // Lọc báo cáo theo khoảng thời gian
    @GetMapping("/filterByDate")
    public ResponseEntity<List<ReportDTO>> filterReportsByDateRange(@RequestParam LocalDateTime startDate,
                                                                    @RequestParam LocalDateTime endDate) {
        List<ReportDTO> reportsDTO = reportService.filterReportsByDateRange(startDate, endDate);
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    // Cập nhật trạng thái của báo cáo
// Cập nhật trạng thái của báo cáo
    @PutMapping("/{id}/status")
    public ResponseEntity<ReportDTO> updateReportStatus(@PathVariable int id, @RequestParam("status") String status, @RequestParam(required = false) String reason) {
        System.out.println(status+id);
        ReportDTO updatedReport = reportService.updateReportStatus(id, status, reason);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

}
