package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import com.example.doantotnghiepbe.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/countPostsGroupedByStatus")
    public ResponseEntity<?> countPostsGroupedByStatus(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.countPostsGroupedByStatus());
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/countActivePostsByMonthAndYear")
    public ResponseEntity<?> countActivePostsByMonthAndYear(@RequestParam("year") int year){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.countActivePostsByMonthAndYear(year));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/countUsers")
    public ResponseEntity<?> countUsers(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.countUsers());
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/countPosts")
    public ResponseEntity<?> countPosts(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.countPosts());
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/countPayments")
    public ResponseEntity<?> countPayments(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.countPayment());
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getTotalRevenue")
    public ResponseEntity<?> getTotalRevenue(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",statisticsService.getTotalRevenue());
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getRevenueByMonth")
    public ResponseEntity<?> getRevenueByMonth(@RequestParam("year") int year){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",paymentService.getRevenueByMonth(year));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
}
