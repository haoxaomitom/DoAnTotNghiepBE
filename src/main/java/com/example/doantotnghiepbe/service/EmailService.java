package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendsendSimpleEmail(String toEmail, String subject, String body);
    String sendVerificationEmail(Long userId, String verificationUrl) throws Exception;

    void sendPaymentResultEmail(String txnRef) throws Exception;

}
