package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.PriceDTO;
import com.example.doantotnghiepbe.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PriceController {

    @Autowired
    private PriceService priceService;

    // GET: Lấy tất cả giá
    @GetMapping
    public ResponseEntity<List<PriceDTO>> getAllPrices() {
        List<PriceDTO> priceDTOs = priceService.getAllPrices();
        return ResponseEntity.ok(priceDTOs);
    }

    // POST: Tạo mới giá
    @PostMapping
    public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO priceDTO) {
        PriceDTO createdPrice = priceService.createPrice(priceDTO);
        return ResponseEntity.ok(createdPrice);
    }

    // PUT: Cập nhật giá theo ID
    @PutMapping("/{id}")
    public ResponseEntity<PriceDTO> updatePrice(@PathVariable Long id, @RequestBody PriceDTO priceDTO) {
        PriceDTO updatedPrice = priceService.updatePrice(id, priceDTO);
        return ResponseEntity.ok(updatedPrice);
    }

    // DELETE: Xóa giá theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
