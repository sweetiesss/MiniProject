package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.message.ResponseMessage;
import com.example.demo1.service.ApartmentService;
import com.example.demo1.service.ContractService;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ApartmentService apartmentService;

    private List<Contract> listOfContract = null;
    private StringBuilder message;
    private int code;
    private Object data;


    @GetMapping

    public ResponseEntity<ResponseMessage> getContract(){
        try {
            if(listOfContract == null) listOfContract = contractService.getAllContract();
            message = new StringBuilder("Load Contract Data Succesfully");
            code = 200;
            data = listOfContract;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
        } catch (Exception e) {
            message = new StringBuilder("Load Contract Data Failed");
            code = 500;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(message.toString(),code,null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addContract( String customerID,
                                                        String apartmentID,
                                                       String startDate,
                                                       String endDate){
        try{
            Customer customer = customerService.findById(customerID);
            Apartment apartment = apartmentService.findById(apartmentID);
            Contract contract = Contract.builder().
                    customerID(customer)
                    .apartmentID(apartment)
                    .startDate(LocalDate.parse(startDate))
                    .endDate(LocalDate.parse(endDate))
                    .build();
            contractService.Save(contractService.getAllContract(), contract);
            message = new StringBuilder("Added Succesfully");
            code = 201;
            data = contract;
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage(message.toString(),code,data));
        }catch(Exception e){
            System.out.printf(customerID);
            System.out.printf(apartmentID);
            System.out.printf(startDate);
            System.out.printf(endDate);
            message = new StringBuilder("Failed to Add");
            code = 409;
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage(message.toString(),code,null));
        }
    }


    @PostMapping("/csv")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("files") MultipartFile[] files) {
            int code;
            boolean CSVCheck = true;
            for(MultipartFile file : files) if(!CSVHelper.hasCSVFormat(file)){
                CSVCheck = false;
                break;
            }
            if (CSVCheck) {
                try {
                    boolean formatter = false;
                    message = new StringBuilder("Uploaded the file successfully: ");
                    for(MultipartFile file : files){
                        contractService.save(file, contractService.getAllContract());
                        if(formatter) message.append(", ").append(file.getOriginalFilename());
                        else{
                            message.append(file.getOriginalFilename());
                            formatter = true;
                        }
                    }

                    code = 200;

                    List<Contract> data = contractService.getAllContract();

                    return ResponseEntity.status(HttpStatus.OK)
                                         .body(new ResponseMessage(message.toString(), code, data));
                } catch (Exception e) {
                    boolean formatter = false;

                    message = new StringBuilder("Could not upload the file: ");
                    for(MultipartFile file : files){
                        if(formatter) message.append(", ").append(file.getOriginalFilename());
                        else{
                            message.append(file.getOriginalFilename());
                            formatter = true;
                        }
                    }

                    code = 409;

                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                                         .body(new ResponseMessage(message.toString(), code, null));
                }
            }
            code = 204;
            message = new StringBuilder("Please upload a csv file!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ResponseMessage(message.toString(), code, null));

    }
}
