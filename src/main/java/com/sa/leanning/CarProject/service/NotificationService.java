package com.sa.leanning.CarProject.service;


import java.util.List;

import com.sa.leanning.CarProject.DTO.NotificationCreateRequest;
import com.sa.leanning.CarProject.DTO.NotificationResponse;

public interface NotificationService {

    NotificationResponse create(NotificationCreateRequest request);

    List<NotificationResponse> list();

    long unreadCount();

    void markRead(Long id);

    void delete(Long id);
}