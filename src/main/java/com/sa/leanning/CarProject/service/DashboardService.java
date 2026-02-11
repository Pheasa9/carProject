package com.sa.leanning.CarProject.service;

import java.time.LocalDate;

import com.sa.leanning.CarProject.DTO.TodayDashboardVm;

public interface DashboardService {
	TodayDashboardVm getTodayDashboard(LocalDate day);
}
