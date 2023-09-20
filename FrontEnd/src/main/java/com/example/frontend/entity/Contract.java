package com.example.frontend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder // builder design pattern
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contract {

    private String id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customerID;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Apartment apartmentID;

    private LocalDate startDate;

    private LocalDate endDate;

}
