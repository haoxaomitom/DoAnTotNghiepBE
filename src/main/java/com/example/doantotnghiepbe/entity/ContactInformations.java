package com.example.doantotnghiepbe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_information_id")
    private Long contactInformationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "type_car", length = 100)
    private String typeCar;

    @Column(name = "contact_time", length = 255)
    private String contactTime;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "watched")
    private boolean watched;

    @Column(name = "contacted")
    private boolean contacted;

    @Column(name = "create_at")
    private LocalDateTime createAt = LocalDateTime.now();
}
