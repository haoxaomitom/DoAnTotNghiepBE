package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public Page<Payment> getPaymentsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findPaymentsByUserId(userId, pageable);
    }

    public Page<Payment> getPaymentsByCriteria(Long userId, Long paymentId, Integer postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findPaymentsByCriteria(userId, paymentId, postId, pageable);
    }

    public List<Object[]> getRevenueByMonth(int year) {
        return paymentRepository.getRevenueByMonth(year);
    }

}
