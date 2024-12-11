package com.example.doantotnghiepbe.dto;

import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Users;
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
    private Integer postId;
    private String parkingName;
    private Double price;
    private String priceUnit;
    private String wardName;
    private String districtName;
    private String provinceName;
    private String status;
    private Integer commentCount;
    private LocalDateTime topPostEnd;
    private LocalDateTime createdAt;
    private List<Image> images;
    private List<Amenities> amenities;
    private List<VehicleType> vehicleTypes;
    private PostUserDTO user;
    private Long userId;
}


