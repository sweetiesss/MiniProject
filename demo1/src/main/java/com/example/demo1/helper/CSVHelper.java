package com.example.demo1.helper;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.service.ApartmentService;
import com.example.demo1.service.ContractService;
import com.example.demo1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

public class CSVHelper {
    public static String TYPE = "text/csv";
    private static final String[] CUSTOMERHEADER = { "FirstName", "LastName", "Address", "Age", "Status" };
    private static final String[] APARTMENTHEADER = { "Address", "RetalPrice", "NumberOfRoom"};
    private static final String[] CONTRACTHEADER = { "customerID", "apartmentID", "startDate", "endDate"};


    private static ApartmentService apartmentService;
    private static CustomerService customerService;
    private static ContractService contractService;
    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


    public static List<Customer> CSVCustomer(InputStream is, List<Customer> currCustomer) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Customer> customerList = new ArrayList<Customer>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();



            for (CSVRecord csvRecord : csvRecords) {
                Customer customer = Customer.builder()
                        .firstName(csvRecord.get(CUSTOMERHEADER[0]))
                        .lastName(csvRecord.get(CUSTOMERHEADER[1]))
                        .address(csvRecord.get(CUSTOMERHEADER[2]))
                        .age(Integer.parseInt(csvRecord.get(CUSTOMERHEADER[3])))
                        .status(csvRecord.get(CUSTOMERHEADER[4]))
                        .build();
                if(!customerService.duplicateCheck(customer, currCustomer)) customerList.add(customer);
            }

            return customerList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Apartment> CSVApartment(InputStream is, List<Apartment> currApartments) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Apartment> apartmentList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Apartment apartment = Apartment.builder()
                        .address(csvRecord.get(APARTMENTHEADER[0]))
                        .retalPrice(csvRecord.get(APARTMENTHEADER[1]))
                        .numberOfRoom(Integer.parseInt(csvRecord.get(APARTMENTHEADER[2])))
                        .build();
                if(!apartmentService.duplicateCheck(apartment, currApartments)) apartmentList.add(apartment);
            }

            return apartmentList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Contract> CSVContract (InputStream is, List<Contract> currContract) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Contract> contractList = new ArrayList<Contract>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                Customer customer = customerService.findById(csvRecord.get(CONTRACTHEADER[0]));
                Apartment apartment = apartmentService.findById(csvRecord.get(CONTRACTHEADER[1]));
                Contract contract = Contract.builder()
                        .customerID(customer)
                        .apartmentID(apartment)
                        .startDate(LocalDate.parse(csvRecord.get(CONTRACTHEADER[2])))
                        .endDate(LocalDate.parse(csvRecord.get(CONTRACTHEADER[3])))
                        .build();

                if(!contractService.duplicateCheck(contract, currContract))contractList.add(contract);
            }

            return contractList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }



}
