package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "parking_name")
    private String parkingName;

    @Column(name = "street")
    private String street;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_unit")
    private String priceUnit;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "top_post_end")
    private LocalDateTime topPostEnd;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Amenities> amenities = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<VehicleType> vehicleTypes = new ArrayList<>();


}
