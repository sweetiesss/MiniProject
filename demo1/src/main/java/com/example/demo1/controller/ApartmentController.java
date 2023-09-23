package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.message.ResponseMessage;
import com.example.demo1.service.ApartmentService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/apartment")
public class ApartmentController {


    private static final Logger logger = LogManager.getLogger("log4j2Logger");

    @Autowired
    private ApartmentService apartmentService;
    private List<Apartment> listOfApartment = null;
    private StringBuilder message;
    private int code;
    private Object data;

    @GetMapping
    public ResponseEntity<ResponseMessage> getApartment(){
        logger.info("getting a List of all Apartments");
        try {
            if(listOfApartment == null) listOfApartment = apartmentService.getAllApartment();
            logger.info("Apartment List loaded successfully!");
            message = new StringBuilder("Load Apartment Data Succesfully");
            code = 200;
            data = listOfApartment;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message.toString(),code,data));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Load Data failed");
            message = new StringBuilder("Load Apartment Data Failed");
            code = 500;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ResponseMessage(message.toString(),code,null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addApartment(String address, String retalPrice, Integer numberOfRoom){
        logger.info("Attempting to add an Apartment");
        try{
            Apartment apartment = Apartment.builder()
                    .address(address)
                    .retalPrice(retalPrice)
                    .numberOfRoom(numberOfRoom)
                    .build();
            logger.info("Successfully build a new Apartment Object");
            apartmentService.save(apartmentService.getAllApartment(), apartment);
            message = new StringBuilder("Added Succesfully");
            code = 201;
            data = apartment;
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
        StringBuilder message;
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
                    apartmentService.save(file, apartmentService.getAllApartment());
                    if(formatter) message.append(", ").append(file.getOriginalFilename());
                    else{
                        message.append(file.getOriginalFilename());
                        formatter = true;
                    }
                }
                logger.info("All files upload successfully!");
                code = 200;

                List<Apartment> data = apartmentService.getAllApartment();

                return ResponseEntity.status(HttpStatus.OK)
                                     .body(new ResponseMessage(message.toString(),code,data));
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
        message = new StringBuilder("Please upload only csv file!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ResponseMessage(message.toString(), code, null));
    }
}
