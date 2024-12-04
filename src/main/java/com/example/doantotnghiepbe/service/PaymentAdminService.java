package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentAdminDTO;
import com.example.doantotnghiepbe.entity.Payment;

import java.util.List;

public interface PaymentAdminService {
    List<PaymentAdminDTO> getAllPayments(); // Lấy tất cả payments
    PaymentAdminDTO getPaymentById(Integer id); // Lấy payment theo ID
    PaymentAdminDTO updatePaymentStatus(Integer id, String status); // Cập nhật trạng thái
    Payment savePayment(Payment payment); // Lưu payment mới
}
