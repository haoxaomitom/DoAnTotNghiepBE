package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpPostDTO {
    private Integer postId;

    private Integer userId;

    private String parkingName;

    private String street;

    private String wardName;

    private String districtName;

    private String provinceName;

    private Double price;


    private String priceUnit;

    private Integer capacity;

    private Double latitude;

    private Double longitude;

    private String description;

    private String status;

    private Integer commentCount;

    private LocalDateTime createdAt;
    private List<String> images;
    private List<String> amenities;
}
