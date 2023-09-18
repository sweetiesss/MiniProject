package com.example.frontend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String age;
    private String status;

    public Customer(String id, String firstName, String lastName, String address, String age, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
