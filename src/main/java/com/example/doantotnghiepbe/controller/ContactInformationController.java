package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.service.ContactInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.doantotnghiepbe.exception.DataNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contactInformation")
public class ContactInformationController {
    @Autowired
    private ContactInformationService contactInformationService;

    @GetMapping("/getByPostId/{id}")
    public ResponseEntity<?> getByPostId(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findAllByPostId(id));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getByPostIdAndWatched/{id}")
    public ResponseEntity<?> getByPostIdAndWatched(@PathVariable Integer id, @RequestParam("watched") boolean watched){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findByWatched(id,watched));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ContactInformationDTO contactInformationDTO){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.create(contactInformationDTO));
        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message",e);
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ContactInformationDTO contactInformationDTO, @PathVariable Long id){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.update(id,contactInformationDTO));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> watched(@PathVariable Long id){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.watched(id));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }

}
