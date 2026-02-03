package com.sa.leanning.CarProject.ApiException;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;

@Data
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
