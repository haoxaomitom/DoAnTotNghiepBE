package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    String username;

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
}
