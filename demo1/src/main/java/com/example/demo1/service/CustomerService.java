package com.example.demo1.service;

import com.example.demo1.entity.Customer;
import com.example.demo1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerReposiroty;

    public List<Customer> getAllCustomer(){
        List<Customer> all = customerReposiroty.findAll();
        return all;
    }

    public void save(Customer customer) {
        customerReposiroty.save(customer);
    }

    public Customer findById(String customerID) {
        return customerReposiroty.findById(customerID).orElse(null);
    }
}
