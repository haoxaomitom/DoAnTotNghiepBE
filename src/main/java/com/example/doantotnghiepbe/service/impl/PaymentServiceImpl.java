package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PaymentUserDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PaymentUserDTO> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepository.findPaymentsByUserId(userId);
        return payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PaymentUserDTO convertToDTO(Payment payment) {
        // Use ModelMapper to convert Payment to PaymentUserDTO
        return modelMapper.map(payment, PaymentUserDTO.class);
    }

}
