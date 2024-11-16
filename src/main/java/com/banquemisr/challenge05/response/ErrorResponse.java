package com.banquemisr.challenge05.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int statusCode;
    private String status;
    private String errorMessage;

    public ErrorResponse(int statusCode, String status, String errorMessage) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.status = status;
        this.errorMessage = errorMessage;
    }

}
