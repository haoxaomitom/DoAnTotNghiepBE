package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @RequestMapping("/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam("userId") Long userId){
        Map<String, Object> result = new HashMap<>();
        String verificationUrl = "http://localhost:8080/api/users/verified?token=" ;
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",emailService.sendVerificationEmail(userId,verificationUrl));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e);
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @RequestMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String username) {
        Map<String, Object> result = new HashMap<>();
        try {
            emailService.sentResetPasswordEmail(username);
            result.put("status", true);
            result.put("message","Thành công");

        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
        }
        return ResponseEntity.ok(result);
    }

}
