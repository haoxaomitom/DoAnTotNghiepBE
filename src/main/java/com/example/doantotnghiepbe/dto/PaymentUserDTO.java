package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUserDTO {
    private Long paymentId;
    private Long postId;
    private Integer paymentAmount;
    private String currency;
    private LocalDateTime paymentDate;
    private LocalDateTime topPostEnd;
    private String paymentStatus;
    private String vnpTxnRef;
}
