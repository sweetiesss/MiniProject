package com.example.frontend.controller;

import com.example.frontend.entity.Contract;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller

public class ContractController {
    @GetMapping("/contract")
    public String showContract(Model model) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8081/api/Contract")).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Gson gson = createGson();

        Type contractListType = new TypeToken<List<Contract>>() {}.getType();
        List<Contract> contract = gson.fromJson(response.body(), contractListType);


        model.addAttribute("contract", contract);
        return "contractList";
    }

    private Gson createGson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
            String dateStr = json.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(dateStr, formatter);
        });
        return gsonBuilder.create();
    }
}
