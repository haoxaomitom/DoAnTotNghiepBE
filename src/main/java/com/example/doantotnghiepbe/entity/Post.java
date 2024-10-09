package com.example.doantotnghiepbe.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;

    @Column(name = "username")
    private String username;

    @Column(name = "parking_name")
    private String parking_name;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_per")
    private String price_per;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "ward_name")
    private String ward_name;

    @Column(name = "district_name")
    private String district_name;

    @Column(name = "province_name")
    private String province_name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "image")
    private String image;

    @Column(name = "opening_hours")
    private LocalTime opening_hours;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    // Many-to-many relationship with Amenities through PostAmenities
    @ManyToMany
    @JoinTable(
            name = "post_amenities",  // The name of the join table
            joinColumns = @JoinColumn(name = "id_post"),  // Foreign key in the join table referencing Post
            inverseJoinColumns = @JoinColumn(name = "id_amenities")  // Foreign key in the join table referencing Amenities
    )
    private Set<Amenities> amenities = new HashSet<>();

    // Constructors
    public Post() {}

    public Post(String username, String parking_name, Double price, String price_per, Integer capacity, String ward_name, String district_name, String province_name, Double latitude, Double longitude, String image, LocalTime opening_hours, String status) {
        this.username = username;
        this.parking_name = parking_name;
        this.price = price;
        this.price_per = price_per;
        this.capacity = capacity;
        this.ward_name = ward_name;
        this.district_name = district_name;
        this.province_name = province_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.opening_hours = opening_hours;
        this.status = status;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Set<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenities> amenities) {
        this.amenities = amenities;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Method to add an amenity to the post
    public void addAmenity(Amenities amenity) {
        this.amenities.add(amenity);
    }

    // Method to remove an amenity from the post
    public void removeAmenity(Amenities amenity) {
        this.amenities.remove(amenity);
    }
}
