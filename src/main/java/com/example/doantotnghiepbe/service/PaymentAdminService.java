package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentAdminDTO;
import com.example.doantotnghiepbe.entity.Payment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentAdminService {
    Page<PaymentAdminDTO> getAllPayments(int page, int size);

    PaymentAdminDTO getPaymentById(Integer id); // Lấy payment theo ID
    PaymentAdminDTO updatePaymentStatus(Integer id, String status); // Cập nhật trạng thái
    Payment savePayment(Payment payment); // Lưu payment mới

    Page<Payment> getPaymentsByCriteria(Long userId, Long paymentId, Integer postId, int page, int size);
}
