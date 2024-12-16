package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;
import com.example.doantotnghiepbe.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment/{priceId}/{postId}")
    public ResponseEntity<?> createPayment(HttpServletRequest req, @PathVariable Integer priceId, @PathVariable Integer postId) throws UnsupportedEncodingException {
        PaymentResDTO paymentResDTO = paymentService.createPayment(req, priceId, postId);
        return ResponseEntity.ok(paymentResDTO);
    }

    @GetMapping("/return")
    public void handleVNPayReturn(HttpServletRequest req, HttpServletResponse response) throws IOException {
        paymentService.handleVNPayReturn(req, response);
    }

    @GetMapping("/payment/details/{txnRef}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable String txnRef) {
        PaymentSuccessDTO paymentSuccessDTO = paymentService.getPaymentDetails(txnRef);
        if (paymentSuccessDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }
        return ResponseEntity.ok(paymentSuccessDTO);
    }
    @GetMapping("/getRevenueByMonth")
    public ResponseEntity<?> getRevenueByMonth(@RequestParam("year") int year){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",paymentService.getRevenueByMonth(year));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
}
