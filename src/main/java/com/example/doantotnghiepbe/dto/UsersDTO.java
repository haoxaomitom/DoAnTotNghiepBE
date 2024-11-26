package com.example.doantotnghiepbe.dto;

import com.example.doantotnghiepbe.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    String username;

    String password;

    String firstName;

    String lastName;

    Roles roles;

    String email;

    Boolean verified;

    Boolean isActive;

    LocalDateTime createAt = LocalDateTime.now();

    String  confirmPassword;
}
