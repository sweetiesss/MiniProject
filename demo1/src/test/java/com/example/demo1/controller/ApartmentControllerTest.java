package com.example.demo1.controller;

import com.example.demo1.entity.Apartment;
import com.example.demo1.repository.ApartmentRepository;
import com.example.demo1.repository.ContractRepository;
import com.example.demo1.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApartmentControllerTest {

    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ContractRepository contractRepository;
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
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
        Apartment apartment = Apartment.builder()
                .address("D1, Java street")
                .retalPrice("123123")
                .numberOfRoom(123)
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/apartment/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apartment)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.address",
                        is(apartment.getAddress())))
                .andExpect(jsonPath("$.retalPrice",
                        is(apartment.getRetalPrice())))
                .andExpect(jsonPath("$.numberOfRoom",
                        is(apartment.getNumberOfRoom())));

    }


}