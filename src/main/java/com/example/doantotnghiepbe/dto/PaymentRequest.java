package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long amount;
    private String paymentInfo;
    private Integer postId;
    private Integer priceId;
}
