package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price priceId;

    @Column(name = "payment_amount", nullable = false)
    private Long paymentAmount;

    @Column(name = "payment_info")
    private String paymentInfo;

    @Column(name = "currency", nullable = false)
    private String currency = "VND";

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "top_post_end")
    private LocalDateTime topPostEnd;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus = "Pending";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Thêm trường để lưu txnRef
    @Column(name = "vnp_txn_ref", nullable = false)
    private String vnpTxnRef;

    @Column(name = "vnp_transaction_id")
    private String vnpTransactionId;

}
