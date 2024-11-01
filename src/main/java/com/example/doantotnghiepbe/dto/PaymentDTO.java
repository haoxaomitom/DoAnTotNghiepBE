package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long paymentId;          // ID của giao dịch trong CSDL
    private Integer postId;             // ID bài đăng
    private Integer priceId;            // ID giá
    private Long paymentAmount;     // Số tiền thanh toán
    private String paymentInfo;
    private String currency;          // Loại tiền tệ
    private String paymentStatus;     // Trạng thái thanh toán
    private String paymentMethod;     // Phương thức thanh toán
    private String vnpTransactionId;  // Mã giao dịch từ VNPay
    private String bankCode;          // Mã ngân hàng (nếu có)
    private LocalDateTime paymentDate; // Ngày giờ thanh toán
    private String paymentUrl;        // URL thanh toán
}
