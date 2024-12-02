package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.ReportDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Report;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.ReportRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {


    private final ReportRepository reportRepository;
    private final UsersRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        // Convert DTO to Entity (populate necessary fields)
        Report report = modelMapper.map(reportDTO, Report.class);
        report.setCreatedAt(LocalDateTime.now()); // Set created at server-side

        // Check if userId is provided
        if (reportDTO.getUser() != null) {
            // Fetch and set the user based on userId
            Users user = userRepository.findById(reportDTO.getUser())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            report.setUser(user);
        } else {
            // If userId is not provided, set user as null
            report.setUser(null);
        }

        // Fetch and set the post based on postId
        Post post = postRepository.findById(reportDTO.getPost())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        report.setPost(post);

        // Save entity and convert back to DTO
        report = reportRepository.save(report);
        return convertReportToDTO(report);
    }


    @Override
    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertReportToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(int id) {
        Report report = reportRepository.findById(id).orElse(null);
        return convertReportToDTO(report);
    }

    private ReportDTO convertReportToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }
}