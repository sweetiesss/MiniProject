package com.example.frontend.message;

import lombok.*;

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
