package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "vehicle_type_post") // Ensure this name matches your database
public class VehicleTypePost {

    @Id
    @ManyToOne
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "id_post")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id")
    private VehicleType vehicleType;


    // Constructors, getters, and setters
    public VehicleTypePost(Post post , VehicleType vehicleType) {
        this.post = post;
        this.vehicleType = vehicleType;
    }

    public VehicleTypePost() {

    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
