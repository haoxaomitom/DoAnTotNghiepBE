package com.example.doantotnghiepbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "street", length = 255)
    private String street;

    @Column(name = "ward_name", length = 100)
    private String wardName;

    @Column(name = "district_name", length = 100)
    private String districtName;

    @Column(name = "province_name", length = 100)
    private String provinceName;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role")
    @JsonIgnore  // Add this annotation
    private Role role;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "is_active")
    private Boolean isActive;
}
