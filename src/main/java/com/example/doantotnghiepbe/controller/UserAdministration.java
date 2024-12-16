package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.UsersLoginDTO;
import com.example.doantotnghiepbe.dto.response.LoginRespone;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/administration/user")
public class UserAdministration {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsersLoginDTO userLoginDTO) {
        Map<String,Object> result = new HashMap<>();
        try {
            Map results = usersService.loginAdmin(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            String token = (String) results.get("token");
            Long userId = (Long) results.get("userId");
            result.put("status",true);
            result.put("message", "Đăng nhập thành công");
            result.put("data", LoginRespone.builder()
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
}
