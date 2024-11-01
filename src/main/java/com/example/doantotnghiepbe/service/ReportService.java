package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ReportDTO;
import com.example.doantotnghiepbe.entity.Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO);

    List<ReportDTO> getAllReports();

    ReportDTO getReportById(int id);
}