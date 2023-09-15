package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Apartment {
    @Id
    @Column(name = "ID", nullable = false, length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "Address", length = 45)
    private String address;

    @Column(name = "RetalPrice", length = 45)
    private String retalPrice;

    @Column(name = "NumberOfRoom")
    private Integer numberOfRoom;

}