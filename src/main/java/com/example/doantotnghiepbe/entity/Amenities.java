package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_amenities;

    @Column(name = "amenities_name")
    private String amenities_name;

    // Constructor
    public Amenities() {}

    // Getter và Setter
    public Integer getId_amenities() {
        return id_amenities;
    }

    public void setId_amenities(Integer id_amenities) {
        this.id_amenities = id_amenities;
    }

    public String getAmenities_name() {
        return amenities_name;
    }

    public void setAmenities_name(String amenities_name) {
        this.amenities_name = amenities_name;
    }
}
