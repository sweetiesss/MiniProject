package com.example.demo1.service;

import com.example.demo1.entity.Apartment;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void save(MultipartFile file) {
        try {
            List<Apartment> apartmentList = CSVHelper.CSVApartment(file.getInputStream());
            apartmentRepository.saveAll(apartmentList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public Apartment findById(String apartmentID) {
        return apartmentRepository.findById(apartmentID).orElse(null);
    }
}
