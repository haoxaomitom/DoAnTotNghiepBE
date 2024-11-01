package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

}
