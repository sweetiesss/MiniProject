package com.example.demo1.controller;

import com.example.demo1.service.ApartmentService;
import com.example.demo1.service.ContractService;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;

@RestController
@RequestMapping("/api/CSV")
public class CSVReadController {
    @Autowired
    private ContractController contractController;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private ApartmentController apartmentController;
    @GetMapping
    public void readApartmentCSV(String path){
        String line = "";
        boolean k1 = false, k2 = false, k3 = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line = br.readLine()) != null){
                String[] value = line.split(",");
                if(value.length == 5 || k1 && !k2 && !k3){ // this is Customer file
                    if(!k1) k1 = true;
                    customerController.addCustomer(value[0],
                                                   value[1],
                                                   value[2],
                                                   Integer.parseInt(value[3]),
                                                   value[4]);
                    continue;
                }
                if(value.length == 4 || k2 && !k3 && !k1){ // this is Contract file
                    if(!k2) k2 = true;
                    contractController.addContract(value[0],
                                                   value[1],
                                                   value[2],
                                                   value[3]);
                    continue;
                }
                if(value.length == 3){ // this is Apartment file
                    apartmentController.addApartment(value[0],
                                                     value[1],
                                                     Integer.parseInt(value[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
