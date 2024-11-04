package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PaymentSuccessDTO getPaymentSuccessInfo(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Directly map Payment to PaymentSuccessDTO
        return modelMapper.map(payment, PaymentSuccessDTO.class);
    }
}
