package com.example.demo1.helper;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.service.ApartmentService;
import com.example.demo1.service.CustomerService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {    public static String TYPE = "text/csv";
    private static String[] HEADERs = { "ID", "Title", "Description", "Published" };
    private static ApartmentService apartmentService;
    private static CustomerService customerService;
    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


    public static List<Customer> CSVCustomer(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Customer> customerList = new ArrayList<Customer>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Customer customer = Customer.builder()
                        .firstName(csvRecord.get("FirstName"))
                        .lastName(csvRecord.get("LastName"))
                        .address(csvRecord.get("Address"))
                        .age(Integer.parseInt(csvRecord.get("Age")))
                        .status(csvRecord.get("Status"))
                        .build();
                customerList.add(customer);
            }

            return customerList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Apartment> CSVApartment(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Apartment> apartmentList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Apartment apartment = Apartment.builder()
                        .address(csvRecord.get("Address"))
                        .retalPrice(csvRecord.get("RetalPrice"))
                        .numberOfRoom(Integer.parseInt(csvRecord.get("NumberOfRoom")))
                        .build();
                apartmentList.add(apartment);
            }

            return apartmentList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Contract> CSVContract (InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Contract> contractList = new ArrayList<Contract>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {


                Customer customer = customerService.findById(csvRecord.get("customerID"));
                Apartment apartment = apartmentService.findById(csvRecord.get("apartmentID"));
                Contract contract = Contract.builder()
                        .customerID(customer)
                        .apartmentID(apartment)
                        .startDate(LocalDate.parse(csvRecord.get("startDate")))
                        .endDate(LocalDate.parse(csvRecord.get("endDate")))
                        .build();


                contractList.add(contract);
            }

            return contractList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }



}
