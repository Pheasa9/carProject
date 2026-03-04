package com.sa.leanning.CarProject.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String message;
    private boolean readStatus;

    private String createdBy;
    private LocalDateTime createdAt;
}