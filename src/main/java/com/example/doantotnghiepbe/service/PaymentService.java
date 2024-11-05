package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PaymentDTO;
import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;

import com.example.doantotnghiepbe.dto.PaymentUserDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentService {

    List<PaymentUserDTO> getPaymentsByUserId(Integer userId);

}
