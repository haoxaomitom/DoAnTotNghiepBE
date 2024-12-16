package com.example.doantotnghiepbe.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    List<Object[]> countPostsGroupedByStatus();
    List<Object[]> countActivePostsByMonthAndYear(int year);
    long countPosts();
    long countUsers();
    long countPayment();
    long getTotalRevenue();
}
