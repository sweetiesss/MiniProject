package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/Apartment")
public class ApartmentController {
    @Autowired
    ApartmentService apartmentService;
    @GetMapping
    public List<Apartment> getApartment(){
        return apartmentService.getAllApartment();
    }

    @PostMapping("/add")
    public void addApartment(String address, String retalPrice, int numberOfRoom){
        Apartment apartment = Apartment.builder()
                                       .address(address)
                                       .retalPrice(retalPrice)
                                       .numberOfRoom(numberOfRoom)
                                       .build();
        apartmentService.save(apartment);
    }
}
