package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PostAmenities")
public class PostAmenities implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_post", referencedColumnName = "id_post")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_amenities", referencedColumnName = "id_amenities")
    private Amenities amenities;

    // Constructors
    public PostAmenities() {}

    public PostAmenities(Post post, Amenities amenities) {
        this.post = post;
        this.amenities = amenities;
    }

    // Getters and Setters
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }
}
