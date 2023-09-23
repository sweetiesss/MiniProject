package com.example.demo1.message;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String message;
    private int code;
    private Object data;
}