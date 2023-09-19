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
@RequestMapping("/api/Customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public List<Customer> getCustomer(){
        return customerService.getAllCustomer();
    }

    @PostMapping("/add")
    public void addCustomer(String firstName, String lastName, String address, int age, String status){
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .age(age)
                .status(status)
                .build();
        customerService.save(customer);
    }


@PostMapping("/CSV")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        int code;
        boolean CSVCheck = true;
        for(MultipartFile file : files) if(!CSVHelper.hasCSVFormat(file)){
            CSVCheck = false;
            break;
        }
        if (CSVCheck) {
            try {
                message = "Uploaded the file successfully: ";
                for(MultipartFile file : files){
                    customerService.save(file);
                    message = message + " " + file.getOriginalFilename();
                }
                code = 200;
                List<Customer> data = customerService.getAllCustomer();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,data,code));
            } catch (Exception e) {
                code = 409;

                message = "Could not upload the file: ";
                for(MultipartFile file : files) message = message + " " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, null, code));
            }
        }
        code = 204;
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, null, code));
    }
}
