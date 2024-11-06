package com.example.doantotnghiepbe.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(length = 50, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "roles")
    @JsonManagedReference
    private List<Users> users;

    @OneToMany(mappedBy = "roles")
    @JsonManagedReference
    private List<Authorities> authorities;

}
