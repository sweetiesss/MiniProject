//package com.example.demo1.controller;
//
//import com.example.demo1.entity.Apartment;
//import com.example.demo1.entity.Contract;
//import com.example.demo1.entity.Customer;
//import com.example.demo1.helper.CSVHelper;
//import com.example.demo1.message.ResponseMessage;
//import com.example.demo1.service.ApartmentService;
//import com.example.demo1.service.ContractService;
//import com.example.demo1.service.CustomerService;
//import com.opencsv.CSVReader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//
//
//@RestController
//@RequestMapping("/api/CSV")
//public class CSVReadController {
//    @Autowired
//    private ContractService contractService;
//    @Autowired
//    private ApartmentService apartmentService;
//    @Autowired
//    private CustomerService customerService;
//
//    List<Customer> customerList = null;
//    List<Apartment> apartmentList = null;
//    List<Contract> contractList = null;
////    @GetMapping
////    public void readCSV(String path){
////        String line = "";
////        boolean k1 = false, k2 = false, k3 = false;
////
////        try (CSVReader csvReader = new CSVReader(new FileReader(path));) {
////            String[] value = null;
////            while((value = csvReader.readNext()) != null){
////                if(value.length == 5 || !k2 && !k3 && k1){ // this is Customer file
////                    if(customerList == null) customerList = new ArrayList<>();
////                    if(!k1) k1 = true;
////
////                    Customer customer = Customer.builder()
////                            .firstName(value[0])
////                            .lastName(value[1])
////                            .address(value[2])
////                            .age(Integer.parseInt(value[3]))
////                            .status(value[4])
////                            .build();
////
////                    customerService.save(customer);
////                    continue;
////                }
////                if(value.length == 4 || k2 && !k3 && !k1){ // this is Contract file
////                    if(contractList == null) contractList = new ArrayList<>();
////                    if(!k2) k2 = true;
////
////                    Customer customer = customerService.findById(value[0]);
////                    Apartment apartment = apartmentService.findById(value[1]);
////                    Contract contract = Contract.builder()
////                            .customerID(customer)
////                            .apartmentID(apartment)
////                            .startDate(LocalDate.parse(value[2]))
////                            .endDate(LocalDate.parse(value[3]))
////                            .build();
////
////
////
////                    contractService.addContract(value[0],
////                                                   value[1],
////                                                   value[2],
////                                                   value[3]);
////                    continue;
////                }
////                if(value.length == 3 || !k2 && k3 && !k1){ // this is Apartment file
////                    if(apartmentList == null) apartmentList = new ArrayList<>();
////                    if(!k3) k3 = true;
////                    apartmentController.addApartment(value[0],
////                                                     value[1],
////                                                     Integer.parseInt(value[2]));
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//    }
//
