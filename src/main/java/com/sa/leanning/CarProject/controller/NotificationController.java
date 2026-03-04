package com.sa.leanning.CarProject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.NotificationCreateRequest;
import com.sa.leanning.CarProject.DTO.NotificationResponse;
import com.sa.leanning.CarProject.service.NotificationService;

import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponse create(@RequestBody NotificationCreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<NotificationResponse> list() {
        return service.list();
    }

    @GetMapping("/unread-count")
    public long unreadCount() {
        return service.unreadCount();
    }

    @PutMapping("/{id}/read")
    public void markRead(@PathVariable Long id) {
        service.markRead(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}