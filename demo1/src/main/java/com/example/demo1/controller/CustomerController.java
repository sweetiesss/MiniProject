package com.example.demo1.controller;

import com.example.demo1.entity.Customer;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
