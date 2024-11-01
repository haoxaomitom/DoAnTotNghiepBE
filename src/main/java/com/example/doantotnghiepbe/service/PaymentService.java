package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentDTO;
import com.example.doantotnghiepbe.entity.Payment;

import java.util.Map;

public interface PaymentService {
    Payment processPaymentReturn(String txnRef, String transactionNo, String amount, String paymentStatus);
}
