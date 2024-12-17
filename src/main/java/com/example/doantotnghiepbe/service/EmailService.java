package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendsendSimpleEmail(String toEmail, String subject, String body);
    String sendVerificationEmail(Long userId, String verificationUrl) throws Exception;
    void sentResetPasswordEmail(String username) throws DataNotFoundException;
}
