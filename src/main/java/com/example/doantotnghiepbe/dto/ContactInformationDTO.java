package com.example.doantotnghiepbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationDTO {

    private Long user;
    private int post;
    private String typeCar;
    private String contactTime;
    private String description;

    
}
