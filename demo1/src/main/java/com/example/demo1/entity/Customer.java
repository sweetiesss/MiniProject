package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "ID", nullable = false, length = 45)
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "FirstName", length = 45)
    private String firstName;

    @Column(name = "LastName", length = 45)
    private String lastName;

    @Column(name = "Address", length = 45)
    private String address;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "Status", length = 45)
    private String status;

}