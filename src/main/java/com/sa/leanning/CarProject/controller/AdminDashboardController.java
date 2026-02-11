package com.sa.leanning.CarProject.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.TodayDashboardVm;
import com.sa.leanning.CarProject.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/today/{date}")
    public TodayDashboardVm today(@PathVariable LocalDate date) {
        return dashboardService.getTodayDashboard(date);
    }
}
