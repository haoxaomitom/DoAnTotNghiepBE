package com.example.doantotnghiepbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    @Column(name = "contact_information_id")
    private Long contactInformationId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;

    @Column(name = "type_car", length = 100)
    private String typeCar;

    @Column(name = "contact_time", length = 255)
    private String contactTime;

    @Column(name = "description", length = 255)
    private String description;
}
