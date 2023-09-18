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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/Contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ApartmentService apartmentService;
    @GetMapping
    public List<Contract> getContract(){
        return contractService.getAllContract();
    }

    @PostMapping
    public void addContract(String customerID, String apartmentID, String startDate, String endDate){
        Customer customer = customerService.findById(customerID);
        Apartment apartment = apartmentService.findById(apartmentID);
        Contract contract = Contract.builder()
                                    .customerID(customer)
                                    .apartmentID(apartment)
                                    .startDate(LocalDate.parse(startDate))
                                    .endDate(LocalDate.parse(endDate))
                                    .build();
        contractService.Save(contract);
    }

    @PostMapping("/CSV")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        int code;
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                contractService.save(file);
                code = 200;
                List<Contract> data = contractService.getAllContract();
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,data,code));
            } catch (Exception e) {
                code = 409;
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, null, code));
            }
        }
        code = 204;
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, null, code));
    }
}
