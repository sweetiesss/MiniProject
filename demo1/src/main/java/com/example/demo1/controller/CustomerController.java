package com.example.demo1.controller;

import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.message.ResponseMessage;
import com.example.demo1.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    private static final Logger logger = LogManager.getLogger("log4j2Logger");

    @Autowired
    private CustomerService customerService;
    private List<Customer> listOfCustomer = null;
    private StringBuilder message;
    private int code;
    private Object data;


    @GetMapping
    public ResponseEntity<ResponseMessage> getCustomer(){

        try {
            logger.info("getting a List of all Customer");
            if(listOfCustomer == null) listOfCustomer = customerService.getAllCustomer();
            logger.info("Load Customer Data Successfully");
            message = new StringBuilder("Load Customer Data Successfully");
            code = 200;
            data = listOfCustomer;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Load Customer Data failed");
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
        logger.info("Attempting to add a Customer");
        try{
            Customer customer = Customer.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .address(address)
                    .age(age)
                    .status(status)
                    .build();
            logger.info("Successfully build a new Customer Object");
            customerService.save(customerService.getAllCustomer(), customer);
            message = new StringBuilder("Added Succesfully");
            code = 201;
            data = customer;
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage(message.toString(),code,data));
        }catch(Exception e){
            logger.error(e.getMessage());
            logger.error("Fail to add");
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
        logger.info("Checking all files...");
        for(MultipartFile file : files) if(!CSVHelper.hasCSVFormat(file)){
            CSVCheck = false;
            break;
        }
        logger.info("All files checked!");
        if (CSVCheck) {
            logger.info("All files have the right format");
            try {
                boolean formatter = false;
                message = new StringBuilder("Uploaded the file successfully: ");
                logger.info("Uploading...");
                for(MultipartFile file : files){
                    customerService.save(file, customerService.getAllCustomer());
                    if(formatter) message.append(", ").append(file.getOriginalFilename());
                    else{
                        message.append(file.getOriginalFilename());
                        formatter = true;
                    }
                }
                logger.info("All files upload successfully!");
                code = 200;
                List<Customer> data = customerService.getAllCustomer();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("Upload failed");
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
    logger.info("Non-csv file detected!");
    logger.info("Please upload only csv file.");
    code = 204;
    message = new StringBuilder("Please upload a csv file!");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(new ResponseMessage(message.toString(), code, null));
    }
}
