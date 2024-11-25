package com.example.doantotnghiepbe.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendsendSimpleEmail(String toEmail, String subject, String body);
    String sendVerificationEmail(Long userId, String verificationUrl) throws Exception;
}
