package com.example.doantotnghiepbe.service;


import com.example.doantotnghiepbe.entity.Payment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentService {


    Page<Payment> getPaymentsByUserId(Long userId, int page, int size);

    Page<Payment> getPaymentsByCriteria(Long userId, Long paymentId, Integer postId, int page, int size);


    Object getRevenueByMonth(int year);
}
