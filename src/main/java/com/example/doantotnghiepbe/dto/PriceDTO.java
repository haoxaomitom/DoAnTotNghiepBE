package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceDTO {
    private Integer priceId;
    private Integer duration;
    private Long amount;
    private String currency;
    private String description;
    private Integer discountPercentage;

}
