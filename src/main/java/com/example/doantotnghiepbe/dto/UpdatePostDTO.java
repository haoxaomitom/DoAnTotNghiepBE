package com.example.doantotnghiepbe.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class UpdatePostDTO {

    private String parkingName;
    private Double price;
    private String priceUnit;
    private Integer capacity;
    private String street;
    private String wardName;
    private String districtName;
    private String provinceName;
    private Double latitude;
    private Double longitude;
    private String status;
    private LocalDateTime createdAt;
    private String description;
    private List<ImageDTO> images;
    private List<AmenitiesDTO> amenities;
    private List<VehicleTypeDTO> vehicleTypes;;

}
