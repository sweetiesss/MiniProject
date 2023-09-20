package com.example.frontend.controller;

import com.example.frontend.entity.Apartment;
import com.example.frontend.message.ResponseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller

public class ApartmentController {

    @GetMapping("/apartment")
    public String showApartment(Model model) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8081/api/apartment")).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();

        ResponseMessage rm = gson.fromJson(response.body(), ResponseMessage.class);
        List<Apartment> apartmentList = new ObjectMapper().convertValue(rm.getData(), new TypeReference<>() {});
        model.addAttribute("apartment", apartmentList);
        return "apartmentList";
    }
}
