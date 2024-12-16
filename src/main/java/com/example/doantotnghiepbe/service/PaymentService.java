package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentService {
    PaymentResDTO createPayment(HttpServletRequest req, Integer priceId, Integer postId) throws UnsupportedEncodingException;
    void handleVNPayReturn(HttpServletRequest req, HttpServletResponse response) throws IOException;
    PaymentSuccessDTO getPaymentDetails(String txnRef);
    List<Object[]> getRevenueByMonth(int year);
}
