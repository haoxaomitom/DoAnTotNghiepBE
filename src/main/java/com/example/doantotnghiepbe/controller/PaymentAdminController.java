package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PaymentAdminDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.service.PaymentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/payments")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PaymentAdminController {

    @Autowired
    private PaymentAdminService paymentAdminService;

    // Lấy danh sách tất cả thanh toán
    @GetMapping("/all")
    public ResponseEntity<List<PaymentAdminDTO>> getAllPayments() {
        List<PaymentAdminDTO> payments = paymentAdminService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Lấy chi tiết thanh toán theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentAdminDTO> getPaymentById(@PathVariable Integer id) {
        PaymentAdminDTO payment = paymentAdminService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    // Cập nhật trạng thái thanh toán
    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentAdminDTO> updatePaymentStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        PaymentAdminDTO updatedPayment = paymentAdminService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(updatedPayment);
    }

    // Tạo mới một payment
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentAdminService.savePayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
}
