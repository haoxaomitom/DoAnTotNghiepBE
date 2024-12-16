package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.ChangePasswordDTO;
import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UserRegisterDTO;
import com.example.doantotnghiepbe.dto.UsersLoginDTO;
import com.example.doantotnghiepbe.dto.response.LoginRespone;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.exceptions.ExistingException;
import com.example.doantotnghiepbe.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Thành công");
            result.put("data",usersService.getAll());
        }catch (Exception e){
            result.put("status", false);
            result.put("message", e.getLocalizedMessage());
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Thành công");
            result.put("data",usersService.getAllUsers());
        }catch (Exception e){
            result.put("status", false);
            result.put("message", e.getLocalizedMessage());
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsersLoginDTO userLoginDTO) {
        Map<String,Object> result = new HashMap<>();
        try {
            Map results = usersService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            String token = (String) results.get("token");
            Long userId = (Long) results.get("userId");
            result.put("status",true);
            result.put("message", "Đăng nhập thành công");
            result.put("data",LoginRespone.builder()
                    .token(token)
                    .userId(userId)
                    .build());
        }catch (DataNotFoundException | BadCredentialsException e){
            result.put("status", false);
            result.put("message",e.getLocalizedMessage());
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Đăng ký thành công");
            result.put("data",usersService.register(userRegisterDTO));
        }catch (ExistingException | DataNotFoundException e){
            result.put("status", false);
            result.put("message", e.getLocalizedMessage());
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UserInfoDTO userInfoDTO){
    Map<String,Object> result = new HashMap<>();
    try{
        result.put("status",true);
        result.put("message", "Update thành công!");
        result.put("data",usersService.updateUserInfo(userInfoDTO));
    }catch (ExistingException e){
        result.put("status",false);
        result.put("message", "Email đã tồn tại!");
        result.put("data",null);
    }catch ( DataNotFoundException e){
        result.put("status",false);
        result.put("message", "không tìm thấy người dùng");
        result.put("data",null);
    }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getUserByUsername")
    public ResponseEntity<?> getUserByUsername(@Valid @RequestParam("username") String username){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Update thành công!");
            result.put("data",usersService.getUsersByUsername(username));
        }catch (DataNotFoundException e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/avatar/{username}")
    public ResponseEntity<?> uploadAvatar(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws DataNotFoundException, IOException{
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Tải ảnh thành công!");
            result.put("data",usersService.uploadAvatar(username ,file));
        }catch (DataNotFoundException |IOException e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> active(@PathVariable Long id,@RequestParam("active") boolean active){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Thành công!");
            result.put("data",usersService.active(id,active));
        }catch (DataNotFoundException e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/countUser")
    public ResponseEntity<?> countUser(){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Thành công!");
            result.put("data",usersService.countUsers());
        }catch (Exception e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/verified")
    public ResponseEntity<?> verified(@RequestParam("token") String token){
        Map<String,Object> result = new HashMap<>();

        try {
            result.put("status",true);
            result.put("message", "Thành công!");
            result.put("data",usersService.getUserByTokenVerified(token));
        }
        catch (DataNotFoundException | RuntimeException e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("error",e);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{userId}/changePassword")
    public ResponseEntity<?> changePassword(@PathVariable("userId") Long userId, @RequestBody ChangePasswordDTO changePasswordDTO){
        Map<String,Object> result = new HashMap<>();
        try {
            usersService.changePassword(userId,changePasswordDTO);
            result.put("status",true);
            result.put("message","Đổi mật khẩu thành công");
        }catch (DataNotFoundException | BadCredentialsException | IllegalArgumentException e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getUsersByMonthAndRole")
    public ResponseEntity<?> getUsersByMonthAndRole(@RequestParam("year") int year){
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Thành công!");
            result.put("data",usersService.getUsersByMonthAndRole(year));
        }catch (Exception e){
            result.put("status",false);
            result.put("message", e.getLocalizedMessage());
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
}
