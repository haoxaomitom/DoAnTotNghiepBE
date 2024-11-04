package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.dto.PriceDTO;
import com.example.doantotnghiepbe.entity.Price;
import com.example.doantotnghiepbe.repository.PriceRepository;
import com.example.doantotnghiepbe.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ModelMapper modelMapper; // Giả sử bạn đang sử dụng ModelMapper để ánh xạ

    @Override
    public List<PriceDTO> getAllPrices() {
        List<Price> prices = priceRepository.findAll();
        return prices.stream()
                .map(price -> modelMapper.map(price, PriceDTO.class))
                .collect(Collectors.toList());
    }

}
