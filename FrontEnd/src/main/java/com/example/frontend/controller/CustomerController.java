package com.example.frontend.controller;

import com.example.frontend.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

@Controller

public class CustomerController {
//    private RestTemplate restTemplate;
    @GetMapping("/customer")
    public String showCustomers(Model model){
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8081/api/Customer")).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();

       Customer[] customer = gson.fromJson(response.body(), Customer[].class);


        model.addAttribute("customer", customer);
        return "customerList";
    }
}
