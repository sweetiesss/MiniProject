package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.repository.ApartmentRepository;
import com.example.demo1.repository.ContractRepository;
import com.example.demo1.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class APITest {

    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ContractRepository contractRepository;
//    @Autowired
//    ApartmentService apartmentService;
//    @Autowired
//    CustomerService customerService;
//    @Autowired
//    ContractService contractService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        contractRepository.deleteAll();
        apartmentRepository.deleteAll();
        customerRepository.deleteAll();
    }



    @Test
    public void givenApartmentObject_whenCreateApartment_thenReturnSavedApartment() throws Exception{
        // given - precondition or setup
        Apartment apartment = Apartment.builder()
                .address("D1, Java street")
                .retalPrice("123123")
                .numberOfRoom(123)
                .build();

        //setup ULR
        StringBuilder URL = new StringBuilder("/api/apartment");
        URL.append("/add");
        URL.append("?").append("address=").append(apartment.getAddress());
        URL.append("&").append("retalPrice=").append(apartment.getRetalPrice());
        URL.append("&").append("numberOfRoom=").append(apartment.getNumberOfRoom());
        // URL create: "/api/apartment/add?address=D1, Java street&retalPrice=123123&numberOfRoom=123"

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post(URL.toString()));
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(apartment)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.address",
                           is(apartment.getAddress())))
                .andExpect(jsonPath("$.data.retalPrice",
                           is(apartment.getRetalPrice())))
                .andExpect(jsonPath("$.data.numberOfRoom",
                           is(apartment.getNumberOfRoom())));

    }

    @Test
    public void givenListOfApartments_whenGetAllApartments_thenReturnApartmentList() throws Exception{

        // given
        List<Apartment> apartmentList = new ArrayList<>();
        apartmentList.add(Apartment.builder()
                                   .address("This is Address")
                                   .retalPrice("This is RentalPrice")
                                   .numberOfRoom(11111)
                                   .build());
        apartmentList.add(Apartment.builder()
                                   .address("Address-Address")
                                   .retalPrice("RetalPrice-RentalPrice")
                                   .numberOfRoom(22222)
                                   .build());
        apartmentList.add(Apartment.builder()
                                   .address("333")
                                   .retalPrice("3333")
                                   .numberOfRoom(33333)
                                   .build());
        apartmentList.add(Apartment.builder()
                                   .address("qwerqwer")
                                   .retalPrice("qwerqwer")
                                   .numberOfRoom(55555)
                                   .build());
        apartmentRepository.saveAll(apartmentList);

        //URL Setup
        StringBuilder URL = new StringBuilder("/api/apartment");

        // when
        ResultActions response = mockMvc.perform(get(URL.toString()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                           is(apartmentList.size())));
    }

    @Test
    public void givenCustomerObject_whenCreateCustomer_thenReturnSavedCustomer() throws Exception{

        // given - precondition or setup
        Customer customer = Customer.builder()
                                    .firstName("LE")
                                    .lastName("Bao")
                                    .address("D1, C++ street")
                                    .age(19)
                                    .status("ONLINE")
                                    .build();



        //setup ULR
        StringBuilder URL = new StringBuilder("/api/customer");
        URL.append("/add");
        URL.append("?").append("firstName=").append(customer.getFirstName());
        URL.append("&").append("lastName=").append(customer.getLastName());
        URL.append("&").append("address=").append(customer.getAddress());
        URL.append("&").append("age=").append(customer.getAge());
        URL.append("&").append("status=").append(customer.getStatus());
        // URL create(hopefully): "/api/customer/add?firstName=LE&lastName=Bao&address=D1, C++ street&age=19&status=ONLINE"

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post(URL.toString()));
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(apartment)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.firstName",
                        is(customer.getFirstName())))
                .andExpect(jsonPath("$.data.lastName",
                        is(customer.getLastName())))
                .andExpect(jsonPath("$.data.address",
                        is(customer.getAddress())))
                .andExpect(jsonPath("$.data.age",
                        is(customer.getAge())))
                .andExpect(jsonPath("$.data.status",
                        is(customer.getStatus())));

    }

    @Test
    public void givenListOfCustomers_whenGetAllCustomers_thenReturnCustomerList() throws Exception{

        // given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(Customer.builder()
                                 .firstName("LE")
                                 .lastName("Bao")
                                 .address("D1, C++ street")
                                 .age(19)
                                 .status("ONLINE")
                                 .build());
        customerList.add(Customer.builder()
                                 .firstName("LE")
                                 .lastName("Bao")
                                 .address("D1, C++ street")
                                 .age(19)
                                 .status("ONLINE")
                                 .build());
        customerList.add(Customer.builder()
                                 .firstName("LE")
                                 .lastName("Bao")
                                 .address("D1, C++ street")
                                 .age(19)
                                 .status("ONLINE")
                                 .build());
        customerList.add(Customer.builder()
                                 .firstName("LE")
                                 .lastName("Bao")
                                 .address("D1, C++ street")
                                 .age(19)
                                 .status("ONLINE")
                                 .build());
        customerRepository.saveAll(customerList);

        //URL Setup
        StringBuilder URL = new StringBuilder("/api/customer");

        // when
        ResultActions response = mockMvc.perform(get(URL.toString()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                        is(customerList.size())));
    }

    @Test
    public void givenContractObject_whenCreateContract_thenReturnSavedContract() throws Exception{
        // given - precondition or setup

        Apartment apartment = Apartment.builder()
                .address("D1, Java street")
                .retalPrice("123123")
                .numberOfRoom(123)
                .build();

        Customer customer = Customer.builder()
                .firstName("LE")
                .lastName("Bao")
                .address("D1, C++ street")
                .age(19)
                .status("ONLINE")
                .build();

        apartmentRepository.save(apartment);
        customerRepository.save(customer);

        Contract contract = Contract.builder().
                customerID(customer)
                .apartmentID(apartment)
                .startDate(LocalDate.parse("2021-10-10"))
                .endDate(LocalDate.parse("2022-02-02"))
                .build();


        //setup ULR
        StringBuilder URL = new StringBuilder("/api/contract");
        URL.append("/add");
        URL.append("?").append("customerID=").append(customer.getId());
        URL.append("&").append("apartmentID=").append(apartment.getId());
        URL.append("&").append("startDate=").append(contract.getStartDate());
        URL.append("&").append("endDate=").append(contract.getEndDate());

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post(URL.toString()));
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(apartment)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.customerID.id",
                        is(contract.getCustomerID().getId())))
                .andExpect(jsonPath("$.data.apartmentID.id",
                        is(contract.getApartmentID().getId())))
                .andExpect(jsonPath("$.data.startDate",
                        is(contract.getStartDate().toString())))
                .andExpect(jsonPath("$.data.endDate",
                        is(contract.getEndDate().toString())));

    }

    @Test
    public void givenListOfContracts_whenGetAllContracts_thenReturnContractList() throws Exception{

        // given
        List<Contract> contractList = new ArrayList<>();

        Apartment apartment1 = Apartment.builder()
                .address("apartment1")
                .retalPrice("A1111")
                .numberOfRoom(1111)
                .build();

        Customer customer1 = Customer.builder()
                .firstName("customer1")
                .lastName("C1111")
                .address("customer1")
                .age(11)
                .status("customer1")
                .build();

        Apartment apartment2 = Apartment.builder()
                .address("apartment2")
                .retalPrice("apartment2")
                .numberOfRoom(2222)
                .build();

        Customer customer2 = Customer.builder()
                .firstName("customer2")
                .lastName("customer2")
                .address("customer2")
                .age(22)
                .status("customer2")
                .build();

        apartmentRepository.save(apartment1);
        apartmentRepository.save(apartment2);
        customerRepository.save(customer1);
        customerRepository.save(customer2);


        contractList.add(Contract.builder()
                                 .customerID(customer1)
                                 .apartmentID(apartment1)
                                 .startDate(LocalDate.parse("2020-12-27"))
                                 .endDate(LocalDate.parse("2021-11-11"))
                                 .build());
        contractList.add(Contract.builder()
                                 .customerID(customer1)
                                 .apartmentID(apartment2)
                                 .startDate(LocalDate.parse("2020-12-12"))
                                 .endDate(LocalDate.parse("2021-10-10"))
                                 .build());
        contractList.add(Contract.builder()
                                 .customerID(customer2)
                                 .apartmentID(apartment1)
                                 .startDate(LocalDate.parse("2020-03-03"))
                                 .endDate(LocalDate.parse("2021-10-10"))
                                 .build());
        contractList.add(Contract.builder()
                                 .customerID(customer2)
                                 .apartmentID(apartment2)
                                 .startDate(LocalDate.parse("2020-04-04"))
                                 .endDate(LocalDate.parse("2021-10-10"))
                                 .build());


        contractRepository.saveAll(contractList);

        //URL Setup
        StringBuilder URL = new StringBuilder("/api/apartment");

        // when
        ResultActions response = mockMvc.perform(get(URL.toString()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                        is(contractList.size())));
    }





}