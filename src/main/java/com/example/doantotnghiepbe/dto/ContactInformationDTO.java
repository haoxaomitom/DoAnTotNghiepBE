package com.example.doantotnghiepbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationDTO {

    private String fullName;
    private String phoneNumber;
    private String typeCar;
    private String contactTime;
    private String description;
    private boolean watched = false;
    private boolean contacted = false;
    private LocalDateTime createAt = LocalDateTime.now();

    
}
