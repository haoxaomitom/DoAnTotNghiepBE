package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    Price findByPriceId(Integer priceId);
}
