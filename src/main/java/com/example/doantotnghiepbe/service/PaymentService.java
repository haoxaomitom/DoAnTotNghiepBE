package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface PaymentService {

    PaymentSuccessDTO getPaymentSuccessInfo(Long paymentId);
}
