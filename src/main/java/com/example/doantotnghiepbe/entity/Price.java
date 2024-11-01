package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceId;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'VND'")
    private String currency;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    private String description;

}
