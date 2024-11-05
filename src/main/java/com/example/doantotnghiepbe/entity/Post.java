package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "parking_name", nullable = false)
    private String parking_name;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "ward_name", nullable = false)
    private String ward_name;

    @Column(name = "district_name", nullable = false)
    private String district_name;

    @Column(name = "province_name", nullable = false)
    private String province_name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "price_per", nullable = false)
    private String price_per;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "describe", length = 500)
    private String describe;

    @Column(name = "opening_hours", nullable = false)
    private LocalTime opening_hours;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    // Constructors
    public Post() {
        // Default constructor
    }

    public Post(String username, String parking_name, String street, String ward_name, String district_name,
                String province_name, Integer price, String price_per, Integer capacity, String latitude,
                String longitude, String describe, LocalTime opening_hours, String status) {
        this.username = username;
        this.parking_name = parking_name;
        this.street = street;
        this.ward_name = ward_name;
        this.district_name = district_name;
        this.province_name = province_name;
        this.price = price;
        this.price_per = price_per;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.describe = describe;
        this.opening_hours = opening_hours;
        this.status = status;
        this.created_at = LocalDateTime.now(); // Set current time on creation
    }

    // Getters and Setters
    public Long getId_post() {
        return id_post;
    }

    public void setId_post(Long id_post) {
        this.id_post = id_post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPrice_per() {
        return price_per;
    }

    public void setPrice_per(String price_per) {
        this.price_per = price_per;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public LocalTime getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(LocalTime opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    // No setter for created_at, it's set automatically on creation

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
