package com.example.frontend.entity;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;

    private String firstName;

    private String lastName;

    private String address;

    private Integer age;

    private String status;

}
