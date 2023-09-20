package com.example.frontend.controller;

import com.example.frontend.message.ResponseMessage;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller

public class ContractController {
    @GetMapping("/contract")
    public String showContract(Model model) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8081/api/contract")).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();

        ResponseMessage rm = gson.fromJson(response.body(), ResponseMessage.class);
        Object contractList = rm.getData();

        model.addAttribute("contract", contractList);
        return "contractList";
    }
}
