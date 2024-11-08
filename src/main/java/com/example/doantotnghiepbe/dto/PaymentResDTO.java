package com.example.doantotnghiepbe.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResDTO implements Serializable {
    private String status;
    private String message;
    private String url;


}
