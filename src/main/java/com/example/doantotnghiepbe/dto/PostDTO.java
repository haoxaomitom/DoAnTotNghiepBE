package com.example.doantotnghiepbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
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
    private String openingHours;
    private String status;
    private String description;
    private List<ImageDTO> images;
    private List<AmenitiesDTO> amenities;
    private List<VehicleTypeDTO> vehicleTypes;
}

