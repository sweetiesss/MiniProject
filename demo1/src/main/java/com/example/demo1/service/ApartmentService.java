package com.example.demo1.service;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.ApartmentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    private static final Logger logger = LogManager.getLogger("log4j2Logger");
    public List<Apartment> getAllApartment(){
        List<Apartment> all = apartmentRepository.findAll();
        return all;
    }

    public void save(List<Apartment> currApartment, Apartment apartment) {
        if(!duplicateCheck(apartment, currApartment)){
            logger.info("No duplicate Apartment found!");
            logger.info("Saving...");
            apartmentRepository.save(apartment);
            logger.info("Successfully added a new Apartment");
        }else{
            logger.info("Duplicate Apartment found!");
            logger.info("Save process terminated!!");
        }
    }
    public void save(MultipartFile file, List<Apartment> currApartment) {
        try {
            List<Apartment> apartmentList = CSVHelper.CSVApartment(file.getInputStream(), currApartment);
            logger.info("File: " + file.getOriginalFilename() + " upload successfully");
            apartmentRepository.saveAll(apartmentList);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("File: " + file.getOriginalFilename() + " upload failed");
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public Apartment findById(String apartmentID) {
        if(apartmentID == null) return null;
        return apartmentRepository.findById(apartmentID).orElse(null);
    }

    public boolean duplicateCheck(Apartment apartment, List<Apartment> currApartment){
        boolean dup = false;

        String address = apartment.getAddress();
        String retalPrice = apartment.getRetalPrice();
        Integer numberOfRoom = apartment.getNumberOfRoom();

        if(address == null) address = "";
        if(retalPrice == null) retalPrice = "";
        if(numberOfRoom == null) numberOfRoom = -1;

        String tempAddress;
        String tempRetalPrice;
        Integer tempNumberOfRoom;

        boolean k1, k2, k3;
        k1 = k2 = k3 = false;

        for(Apartment apm : currApartment) {

            tempAddress = apm.getAddress();
            tempRetalPrice = apm.getRetalPrice();
            tempNumberOfRoom = apm.getNumberOfRoom();

            if(tempAddress == null) tempAddress = "";
            if(tempRetalPrice == null) tempRetalPrice = "";
            if(tempNumberOfRoom == null) tempNumberOfRoom = -1;


            if(tempAddress.equals(address)) k1 = true;
            if(tempRetalPrice.equals(retalPrice)) k2 = true;
            if(tempNumberOfRoom.equals(numberOfRoom)) k3 = true;

            if(k1 && k2 && k3){
                dup = true;
                break;
            }

            k1 = k2 = k3 = false;
        }
        return dup;
    }
}
