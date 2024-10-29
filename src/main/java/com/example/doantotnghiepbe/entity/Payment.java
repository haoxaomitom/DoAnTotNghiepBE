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
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @Column(name = "currency", nullable = false)
    private String currency = "VND";

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "top_post_end")
    private LocalDateTime topPostEnd;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus = "Pending";

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
