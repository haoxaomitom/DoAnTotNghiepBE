package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.service.ContactInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contactInformation")
public class ContactInformationController {
    @Autowired
    private ContactInformationService contactInformationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.getById(id));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findAllByUserId(id));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getByUserIdAndWatched/{id}")
    public ResponseEntity<?> getByUserIdAndWatched(@PathVariable Integer id, @RequestParam("watched") boolean watched){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findByUserIdAndWatched(id,watched));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
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
            result.put("data",contactInformationService.findByPostIdAndWatched(id,watched));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getByPostIdAndContacted/{id}")
    public ResponseEntity<?> getByPostIdAndContacted(@PathVariable Integer id, @RequestParam("contacted") boolean contacted){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findByPostIdAndContacted(id,contacted));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getByUserIdAndContacted/{id}")
    public ResponseEntity<?> getByUserIdAndContacted(@PathVariable Integer id, @RequestParam("contacted") boolean contacted){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.findByUserIdAndContacted(id,contacted));
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/create/{postId}")
    public ResponseEntity<?> create(@PathVariable("postId") Integer postId, @RequestBody ContactInformationDTO contactInformationDTO){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",contactInformationService.create(contactInformationDTO,postId));
        }catch (DataNotFoundException   e){
            result.put("status", false);
            result.put("message",e);
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/contacted/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam("contacted") boolean contacted){
        Map<String,Object> result = new HashMap<>();
        try {
            contactInformationService.contacted(id,contacted);
            result.put("status", true);
            result.put("message","Thành công");
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/watched/{id}")
    public ResponseEntity<?> watched(@PathVariable Long id){
        Map<String,Object> result = new HashMap<>();
        try {
            contactInformationService.watched(id);
            result.put("status", true);
            result.put("message","Thành công");
        }catch (Exception e){
            result.put("status", false);
            result.put("message","Thất bại");
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws DataNotFoundException {
        Map<String, Object> result = new HashMap<>();
        try {
            contactInformationService.delete(id);
            result.put("status", true);
            result.put("message" ,"Thành công");
        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message" ,e.getLocalizedMessage());
        }
        return ResponseEntity.ok(result);
     }

}
