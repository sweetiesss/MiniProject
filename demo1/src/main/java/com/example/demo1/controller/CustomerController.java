package com.example.demo1.controller;

import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.message.ResponseMessage;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private List<Customer> listOfCustomer = null;
    private StringBuilder message;
    private int code;
    private Object data;


    @GetMapping
    public ResponseEntity<ResponseMessage> getCustomer(){
        try {
            if(listOfCustomer == null) listOfCustomer = customerService.getAllCustomer();
            message = new StringBuilder("Load Customer Data Succesfully");
            code = 200;
            data = listOfCustomer;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
        } catch (Exception e) {
            message = new StringBuilder("Load Customer Data Failed");
            code = 500;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(message.toString(),code,null));
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<ResponseMessage> addCustomer(String firstName,
                                                       String lastName,
                                                       String address,
                                                       int age,
                                                       String status){
        try{
            Customer customer = Customer.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .address(address)
                    .age(age)
                    .status(status)
                    .build();
            customerService.save(customerService.getAllCustomer(), customer);
            message = new StringBuilder("Added Succesfully");
            code = 201;
            data = customer;
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage(message.toString(),code,data));
        }catch(Exception e){
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
                    customerService.save(file, customerService.getAllCustomer());
                    if(formatter) message.append(", ").append(file.getOriginalFilename());
                    else{
                        message.append(file.getOriginalFilename());
                        formatter = true;
                    }
                }
                code = 200;
                List<Customer> data = customerService.getAllCustomer();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
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
