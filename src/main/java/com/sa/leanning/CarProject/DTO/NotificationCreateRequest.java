package com.sa.leanning.CarProject.DTO;

import lombok.Data;

@Data
public class NotificationCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String message;
}