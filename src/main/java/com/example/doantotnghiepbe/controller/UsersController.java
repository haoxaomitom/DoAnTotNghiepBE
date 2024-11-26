package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UsersDTO;
import com.example.doantotnghiepbe.dto.UsersLoginDTO;
import com.example.doantotnghiepbe.dto.response.LoginRespone;
import com.example.doantotnghiepbe.exception.DataNotFoundException;
import com.example.doantotnghiepbe.exception.ExistingException;
import com.example.doantotnghiepbe.service.UsersService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsersController {
    @Autowired
    private UsersService usersService;

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
            result.put("message","Tên người dùng hoặc mật khẩu không đúng");
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsersDTO usersDTO) {
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Đăng ký thành công");
            result.put("data",usersService.register(usersDTO));
        }catch (ExistingException e){
            result.put("status", false);
            result.put("message", "Username đã tồn tại");
            result.put("data", null);
        }catch (DataNotFoundException e){
            result.put("status", false);
            result.put("message", e);
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
            result.put("message", e);
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/avatar/{username}")
    public ResponseEntity<?> uploadAvatar(@PathVariable("username") String username, @ModelAttribute MultipartFile file) throws DataNotFoundException, IOException{
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("status",true);
            result.put("message", "Tải ảnh thành công!");
            result.put("data",usersService.uploadAvatar(username ,file));
        }catch (DataNotFoundException |IOException e){
            result.put("status",false);
            result.put("message", e);
            result.put("data",null);
        }
        return ResponseEntity.ok(result);
    }
//    @PostMapping("/facebook-login")
//    public ResponseEntity<?> facebookLogin(@RequestBody String facebookToken) {
//        try {
//            // Assuming the service method processes the Facebook token, retrieves the user or creates a new one
//            Map<String, Object> result = usersService.loginWithFacebook(facebookToken);
//            String token = (String) result.get("token");
//            Long userId = (Long) result.get("userId");
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", true);
//            response.put("message", "Login successful");
//            response.put("data", LoginRespone.builder().token(token).userId(userId).build());
//            return ResponseEntity.ok(response);
//        } catch (DataNotFoundException e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", false);
//            response.put("message", e.getMessage());
//            response.put("data", null);
//            return ResponseEntity.status(400).body(response);  // Bad Request
//        }
//    }
//
//    @PostMapping("/facebook-register")
//    public ResponseEntity<?> registerWithFacebook(@RequestBody String facebookToken) {
//        try {
//            Map<String, Object> result = usersService.registerWithFacebook(facebookToken);
//            String token = (String) result.get("token");
//            Long userId = (Long) result.get("userId");
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", true);
//            response.put("message", "Registration successful");
//            response.put("data", LoginRespone.builder().token(token).userId(userId).build());
//            return ResponseEntity.ok(response);
//        } catch (DataNotFoundException e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("status", false);
//            response.put("message", e.getMessage());
//            response.put("data", null);
//            return ResponseEntity.status(400).body(response);  // Bad Request
//        }
//    }


//    @GetMapping("/home")
//    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
//        model.addAttribute("name", principal.getAttribute("name"));
//        model.addAttribute("email", principal.getAttribute("email"));
//        model.addAttribute("picture", principal.getAttribute("picture"));
//        return "home";
//    }

}
