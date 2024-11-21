package com.example.doantotnghiepbe.controller;
import com.example.doantotnghiepbe.dto.ReportDTO;
import com.example.doantotnghiepbe.entity.Report;
import com.example.doantotnghiepbe.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@Validated @RequestBody ReportDTO reportDTO) {
        ReportDTO createdReportDTO = reportService.createReport(reportDTO);
        return new ResponseEntity<>(createdReportDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> reportsDTO = reportService.getAllReports();
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable int id) {
        ReportDTO reportDTO = reportService.getReportById(id);
        if (reportDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }
}