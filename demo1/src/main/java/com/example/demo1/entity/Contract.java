package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contract {
    @Id
    @Column(name = "ID", nullable = false, length = 45)
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Customer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApartmentID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Apartment apartmentID;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

}