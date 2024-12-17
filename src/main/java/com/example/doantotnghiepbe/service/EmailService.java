package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendsendSimpleEmail(String toEmail, String subject, String body);

    String sendVerificationEmail(Long userId, String verificationUrl) throws Exception;
    void sentResetPasswordEmail(String username) throws DataNotFoundException;

    void sendPaymentResultEmail(String txnRef) throws Exception;

}
