package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    String username;

    String password;

    String firstName;

    String lastName;

    String phoneNumber;

    Boolean verified;

    Boolean isActive;

    LocalDateTime createAt = LocalDateTime.now();

    String  confirmPassword;
}
