package com.example.demo1.service;

import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerReposiroty;
    private static final Logger logger = LogManager.getLogger("log4j2Logger");

    public List<Customer> getAllCustomer(){
        List<Customer> all = customerReposiroty.findAll();
        return all;
    }

    public void save(List<Customer> currCustomer, Customer customer) {

        if(!duplicateCheck(customer, currCustomer)){
            logger.info("No duplicate Customer found!");
            logger.info("Saving...");
            customerReposiroty.save(customer);
            logger.info("Successfully added a new Customer");
        }else{
            logger.info("Duplicate Customer found!");
            logger.info("Save Process terminated!!");
        }
    }

    public void save(MultipartFile file, List<Customer> currCustomer) {
        try {
            List<Customer> customerList = CSVHelper.CSVCustomer(file.getInputStream(), currCustomer);
            logger.info("File: " + file.getOriginalFilename() + " upload successfully");
            customerReposiroty.saveAll(customerList);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("File: " + file.getOriginalFilename() + " upload successfully");
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public Customer findById(String customerID) {
        if(customerID == null) return null;
        return customerReposiroty.findById(customerID).orElse(null);
    }

    public static boolean duplicateCheck(Customer customer, List<Customer> currCustomer){
        boolean dup = false;

        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String address = customer.getAddress();
        Integer age = customer.getAge();
        String status = customer.getStatus();

        if(firstName == null) firstName = "";
        if(lastName == null) lastName = "";
        if(address == null) address = "";
        if(age == null) age = -1;
        if(status == null) status = "";

        String tempFirstName;
        String tempLastName;
        String tempAddress;
        Integer tempAge;
        String tempStatus;

        boolean k1, k2, k3, k4, k5;
        k1 = k2 = k3 = k4 = k5 = false;

        for(Customer cus : currCustomer){

            tempFirstName = cus.getFirstName();
            tempLastName = cus.getLastName();
            tempAddress = cus.getAddress();
            tempAge = cus.getAge();
            tempStatus = cus.getStatus();

            if(tempFirstName == null) tempFirstName = "";
            if(tempLastName == null) tempLastName = "";
            if(tempAddress == null) tempAddress = "";
            if(tempAge == null) tempAge = -1;
            if(tempStatus == null) tempStatus = "";


            if(tempFirstName.equals(firstName)) k1 = true;
            if(tempLastName.equals(lastName)) k2 = true;
            if(tempAddress.equals(address)) k3 = true;
            if(tempAge.equals(age)) k4 = true;
            if(tempStatus.equals(status)) k5 = true;

            if(k1 && k2 && k3 && k4 && k5){
                dup = true;
                break;
            }

            k1 = k2 = k3 = k4 = k5 = false;

        }
        return dup;
    }

}
