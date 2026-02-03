package com.sa.leanning.CarProject.service;

import java.time.LocalDate;
import java.util.List;

import com.sa.leanning.CarProject.DTO.ExpenseReportDto;
import com.sa.leanning.CarProject.DTO.ProductReportDto;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;
import com.sa.leanning.CarProject.projection.ProductSold;

public interface ReportService {
    List<ProductReportDto> getReport(LocalDate startDate, LocalDate endDate);
    List<ExpenseReportDto> getExpenseReport(LocalDate startDate, LocalDate endDate);
}
