package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PriceDTO;
import com.example.doantotnghiepbe.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public List<PriceDTO> getAllPrices() {
        return priceService.getAllPrices();
    }
}
