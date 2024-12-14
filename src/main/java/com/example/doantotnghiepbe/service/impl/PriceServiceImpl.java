package com.example.doantotnghiepbe.service.impl;

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
    private ModelMapper modelMapper;

    @Override
    public List<PriceDTO> getAllPrices() {
        List<Price> prices = priceRepository.findAll();
        return prices.stream()
                .map(price -> modelMapper.map(price, PriceDTO.class))
                .collect(Collectors.toList());
    }

    public List<Price> getActivePrices() {
        return priceRepository.findByStatus("ACTIVATE");
    }

    @Override
    public PriceDTO createPrice(PriceDTO priceDTO) {
        Price price = modelMapper.map(priceDTO, Price.class);
        Price savedPrice = priceRepository.save(price);
        return modelMapper.map(savedPrice, PriceDTO.class);
    }

    @Override
    public PriceDTO updatePrice(Long id, PriceDTO priceDTO) {
        Price existingPrice = priceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Price not found with ID: " + id));
        modelMapper.map(priceDTO, existingPrice); // Cập nhật dữ liệu từ DTO sang entity
        Price updatedPrice = priceRepository.save(existingPrice);
        return modelMapper.map(updatedPrice, PriceDTO.class);
    }

    @Override
    public void deletePrice(Long id) {
        if (!priceRepository.existsById(id)) {
            throw new RuntimeException("Price not found with ID: " + id);
        }
        priceRepository.deleteById(id);
    }
}
