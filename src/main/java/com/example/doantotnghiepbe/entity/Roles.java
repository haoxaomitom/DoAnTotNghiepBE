package com.example.doantotnghiepbe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idRole;

    String roleName;
    @OneToMany(mappedBy = "idRole")
    @JsonBackReference
    List<Users> usersList;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
