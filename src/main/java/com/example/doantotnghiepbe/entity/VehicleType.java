package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_type")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_post")
    @JsonIgnore  // Prevent circular reference
    private Post post;

    @Column(name = "vehicle_type_name")
    private String vehicleTypeName;


}
