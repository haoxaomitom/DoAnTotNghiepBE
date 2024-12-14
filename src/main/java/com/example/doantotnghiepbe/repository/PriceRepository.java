package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findByPriceId(Integer priceId);
    List<Price> findByStatus(String status);
}
