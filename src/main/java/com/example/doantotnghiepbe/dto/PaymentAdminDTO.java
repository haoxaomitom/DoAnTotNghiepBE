package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdminDTO {
    private Long paymentId;
    private Integer postId;
    private String postName; // Nếu cần lấy tên bài đăng từ Post
    private Integer priceId;
    private Integer priceAmount;
    private Integer paymentAmount;
    private String paymentInfo;
    private String currency;
    private String bankCode;
    private LocalDateTime paymentDate;
    private LocalDateTime topPostEnd;
    private String paymentStatus;
    private String vnpTxnRef;
    private String vnpTransactionId;
}
