package com.sa.leanning.CarProject.ApiException;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}
