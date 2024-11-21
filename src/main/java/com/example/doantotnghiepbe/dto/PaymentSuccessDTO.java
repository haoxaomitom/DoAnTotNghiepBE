package com.example.doantotnghiepbe.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentSuccessDTO {
    private String txnRef;
    private Long paymentAmount;
    private String currency;
    private LocalDateTime paymentDate;
    private String paymentStatus;
    private String paymentInfo;
}
