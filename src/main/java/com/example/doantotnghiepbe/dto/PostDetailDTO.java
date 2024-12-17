package com.example.doantotnghiepbe.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class PostDetailDTO {
    private Integer postId;
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
    private String topPostEnd;
    private List<ImageDTO> images;
    private List<AmenitiesDTO> amenities;
    private List<VehicleTypeDTO> vehicleTypes;
    private PostUserDetailDTO user;
    private Integer userId;
    private List<CommentDTO> comments; // Added field for comments

}
