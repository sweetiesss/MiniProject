package com.example.frontend.entity;

public class Apartment {

    private String id;
    private String address;
    private String retalPrice;
    private String numberOfRoom;

    public Apartment(String id, String address, String retalPrice, String numberOfRoom) {
        this.id = id;
        this.address = address;
        this.retalPrice = retalPrice;
        this.numberOfRoom = numberOfRoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRetalPrice() {
        return retalPrice;
    }

    public void setRetalPrice(String retalPrice) {
        this.retalPrice = retalPrice;
    }

    public String getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }
}
