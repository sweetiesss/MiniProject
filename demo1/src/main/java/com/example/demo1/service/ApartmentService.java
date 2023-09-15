package com.example.demo1.service;

import com.example.demo1.entity.Apartment;
import com.example.demo1.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Apartment> getAllApartment(){
        List<Apartment> all = apartmentRepository.findAll();
        return all;
    }

    public void save(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    public Apartment findById(String apartmentID) {
        return apartmentRepository.findById(apartmentID).orElse(null);
    }
}
