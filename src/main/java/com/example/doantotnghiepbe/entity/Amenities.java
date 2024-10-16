package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAmenities;

    @ManyToOne
    @JoinColumn(name = "id_post")
    @JsonBackReference  // Prevents cyclic references during serialization
    private Post post;

    @Column(name = "amenities_name")
    private String amenitiesName;

}
