package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "VehicleType")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vehicle_type_name")
    private String vehicleTypeName;

    // Constructors, getters, and setters
    public VehicleType() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

}
