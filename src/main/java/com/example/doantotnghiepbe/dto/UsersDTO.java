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

    String street;

    String wardName;

    String districtName;

    String provinceName;

    String email;

    String phoneNumber;

    Date dateOfBirth;

    String gender;

    String avatar;

    Roles idRole;

    Boolean verified;

    Boolean isActive;

    LocalDateTime createAt = LocalDateTime.now();

    String  confirmPassword;
}
