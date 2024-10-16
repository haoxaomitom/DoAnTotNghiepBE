package com.example.doantotnghiepbe.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class PostDetailDTO {
    private Integer idPost;
    private String parkingName;
    private Double price;
    private String pricePer;
    private Integer capacity;
    private String wardName;
    private String districtName;
    private String provinceName;
    private Double latitude;
    private Double longitude;
    private String image;
    private LocalTime openingHours;
    private String status;
    private LocalDateTime createdAt;
    private String description;
    private List<ImageDTO> images;
    private List<AmenitiesDTO> amenities;
    private List<VehicleTypeDTO> vehicleTypes;
    private PostUserDetailDTO user;
}