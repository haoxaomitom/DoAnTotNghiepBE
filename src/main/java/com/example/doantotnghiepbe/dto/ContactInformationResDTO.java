package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationResDTO {
    private Long contactInformationId;
    private int postId;
    private String fullName;
    private String phoneNumber;
    private String typeCar;
    private String contactTime;
    private String description;
    private boolean watched;
    private boolean contacted;
    private LocalDateTime createAt ;
}
