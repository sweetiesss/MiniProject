package com.example.demo1.service;

import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerReposiroty;

    public List<Customer> getAllCustomer(){
        List<Customer> all = customerReposiroty.findAll();
        return all;
    }

    public void save(List<Customer> currCustomer, Customer customer) {

        if(!duplicateCheck(customer, currCustomer)) customerReposiroty.save(customer);
    }

    public void save(MultipartFile file, List<Customer> currCustomer) {
        try {
            List<Customer> customerList = CSVHelper.CSVCustomer(file.getInputStream(), currCustomer);
            customerReposiroty.saveAll(customerList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public Customer findById(String customerID) {
        return customerReposiroty.findById(customerID).orElse(null);
    }

    public boolean duplicateCheck(Customer customer, List<Customer> currCustomer){
        boolean dup = false;

        for(Customer cus : currCustomer)
            if(cus.getFirstName().equals(customer.getFirstName()) &&
                    cus.getLastName().equals(customer.getLastName()) &&
                    cus.getAddress().equals(customer.getAddress()) &&
                    cus.getAge().equals(customer.getAge()) &&
                    cus.getStatus().equals(customer.getStatus())){
                dup = true;
                break;
            }
        return dup;
    }

}
