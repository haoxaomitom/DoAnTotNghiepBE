package com.example.doantotnghiepbe.dto;

import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer idPost;
    private String parkingName;
    private Double price;
    private String pricePer;
    private String userName;
    private String wardName;
    private String districtName;
    private String provinceName;
    private String openingHours;
    private String status;
    private LocalDateTime createdAt;
    private List<Image> images;
    private List<Amenities> amenities;
    private List<VehicleType> vehicleTypes;
}

