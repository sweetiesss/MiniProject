package com.example.frontend.entity;

import java.time.LocalDate;

public class Contract {

    private String id;
    private String customerID;

    private String apartmentID;
    private LocalDate startDate;
    private LocalDate endDate;

    public Contract(String id, String customerID, String apartmentID, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.customerID = customerID;
        this.apartmentID = apartmentID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
