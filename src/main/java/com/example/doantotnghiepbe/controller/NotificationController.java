package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.NotificationDTO;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam("id") Integer id){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.getById(id));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllByGlobalAndUser")
    public ResponseEntity<?> getAllByGlobalAndUser(@RequestParam("userId") Long userId){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.getAllByGlobalAndUser(true,userId));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAllByGlobalAndUserAndIsRead")
    public ResponseEntity<?> getAllByGlobalAndUserAndIsRead(@RequestParam("userId") Long userId, @RequestParam("isRead") boolean isRead){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.getAllByGlobalAndUserAndIsRead(true,userId,isRead));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAllByGlobal")
    public ResponseEntity<?> getAllByGlobal(){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.getAllByGlobal(true));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/createNotificationGlobal")
    public ResponseEntity<?> createNotificationGlobal(@RequestBody NotificationDTO notificationDTO){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.createNotificationGlobal(notificationDTO));
        }catch (Exception e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/createNotificationApproval")
    public ResponseEntity<?> createNotificationApproval(@RequestBody NotificationDTO notificationDTO){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message","Thành công");
            result.put("data",notificationService.createNotificationApproval(notificationDTO));
        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/isRead/{notificationId}")
    public ResponseEntity<?> isRead(@PathVariable("notificationId") Integer notificationId){
        Map<String, Object> result = new HashMap<>();
        try {
            notificationService.isRead(notificationId);
            result.put("status", true);
            result.put("message","Thành công");
        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<?> delete(@PathVariable("notificationId") Integer notificationId){
        Map<String, Object> result = new HashMap<>();
        try {
            notificationService.delete(notificationId);
            result.put("status", true);
            result.put("message","Thành công");

        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());

        }
        return ResponseEntity.ok(result);
    }

}
