package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_amenities;

    @ManyToOne
    @JoinColumn(name = "id_post")
    @JsonBackReference  // Prevents cyclic references during serialization
    private Post post;

    @Column(name = "amenities_name")
    private String amenities_name;

    // Constructor
    public Amenities() {}

    // Getters and setters
    public Integer getId_amenities() {
        return id_amenities;
    }

    public void setId_amenities(Integer id_amenities) {
        this.id_amenities = id_amenities;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getAmenities_name() {
        return amenities_name;
    }

    public void setAmenities_name(String amenities_name) {
        this.amenities_name = amenities_name;
    }
}
