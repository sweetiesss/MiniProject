package com.example.frontend.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Apartment {

    private String id;

    private String address;

    private String retalPrice;

    private Integer numberOfRoom;

}
