package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
private PaymentRepository paymentRepository;

    @Override
    public List<Object[]> countPostsGroupedByStatus() {
        return postRepository.countPostsGroupedByStatus();
    }

    @Override
    public List<Object[]> countActivePostsByMonthAndYear(int year) {
        return postRepository.countActivePostsByMonthAndYear(year);
    }

    @Override
    public long countPosts() {
        return postRepository.count();
    }

    @Override
    public long countUsers() {
        return usersRepository.countUsersByRolesRoleId(2);
    }

    @Override
    public long countPayment() {
        return paymentRepository.countPaymentByPaymentStatus("Success");
    }

    @Override
    public long getTotalRevenue() {
        return paymentRepository.getTotalRevenue();
    }
}
