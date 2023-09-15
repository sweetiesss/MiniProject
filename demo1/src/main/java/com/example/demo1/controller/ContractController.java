package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.service.ApartmentService;
import com.example.demo1.service.ContractService;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/Contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ApartmentService apartmentService;
    @GetMapping
    public List<Contract> getContract(){
        return contractService.getAllContract();
    }

    @PostMapping
    public void addContract(String customerID, String apartmentID, String startDate, String endDate){
        Customer customer = customerService.findById(customerID);
        Apartment apartment = apartmentService.findById(apartmentID);
        Contract contract = Contract.builder()
                                    .customerID(customer)
                                    .apartmentID(apartment)
                                    .startDate(LocalDate.parse(startDate))
                                    .endDate(LocalDate.parse(endDate))
                                    .build();
        contractService.Save(contract);
    }
}
