package com.example.demo1.service;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.ContractRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Service
public class ContractService {
    private static final Logger logger = LogManager.getLogger("log4j2Logger");
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContract(){
        return contractRepository.findAll();
    }

    public void Save(List<Contract> currContract, Contract contract){
            if(!duplicateCheck(contract, currContract)){
                logger.info("No duplicate Contract found!");
                logger.info("Saving...");
                contractRepository.save(contract);
                logger.info("Successfully added a new Contract");
            }else{
                logger.info("Duplicate Contract found!");
                logger.info("Save Process terminated!!");
            }
    }

    public void save(MultipartFile file, List<Contract> currContract) {
        try {
            List<Contract> contractList = CSVHelper.CSVContract(file.getInputStream(), currContract);
            logger.info("File: " + file.getOriginalFilename() + " upload successfully");
            contractRepository.saveAll(contractList);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("File: " + file.getOriginalFilename() + " upload failed!");
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public boolean duplicateCheck(Contract contract, List<Contract> currContract){
        boolean dup = false;

        String customerID;
        String apartmentID;
        LocalDate startDate = contract.getStartDate();
        LocalDate endDate = contract.getEndDate();

        if(contract.getCustomerID() != null) customerID = contract.getCustomerID().getId();
        else customerID = "";
        if(contract.getApartmentID() != null) apartmentID = contract.getApartmentID().getId();
        else apartmentID = "";
        if(startDate == null) startDate = LocalDate.parse("1-1-1");
        if(endDate == null) endDate = LocalDate.parse("1-1-1");

        String tempCustomerID;
        String tempApartmentID;
        LocalDate tempStartDate;
        LocalDate tempEndDate;

        boolean k1, k2, k3, k4;
        k1 = k2 = k3 = k4 = false;

        for(Contract cont : currContract){

            if(cont.getCustomerID() != null) tempCustomerID = cont.getCustomerID().getId();
            else tempCustomerID = ""; // initialize tempCustomerID
            if(cont.getApartmentID() != null) tempApartmentID = cont.getApartmentID().getId();
            else tempApartmentID = ""; // initialize tempApartmentID
            tempStartDate = cont.getStartDate();
            tempEndDate = cont.getEndDate();


            if(tempStartDate == null) tempStartDate = LocalDate.parse("1-1-1");
            if(tempEndDate == null) tempEndDate = LocalDate.parse("1-1-1");


            if(tempCustomerID.equals(customerID)) k1 = true;
            if(tempApartmentID.equals(apartmentID)) k2 = true;
            if(tempStartDate.isEqual(startDate)) k3 = true;
            if(tempEndDate.isEqual(endDate)) k4 = true;

            if(k1 && k2 && k3 && k4){
                dup = true;
                break;
            }

            k1 = k2 = k3 = k4 = false;
        }
        return dup;
    }
}
