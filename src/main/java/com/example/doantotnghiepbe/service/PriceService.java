package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.PriceDTO;

import java.util.List;

public interface PriceService {
    List<PriceDTO> getAllPrices();

    PriceDTO createPrice(PriceDTO priceDTO);

    PriceDTO updatePrice(Long id, PriceDTO priceDTO);

    void deletePrice(Long id);
}