package com.sa.leanning.CarProject.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.ExpenseReportDto;
import com.sa.leanning.CarProject.DTO.ProductReportDto;
import com.sa.leanning.CarProject.projection.ProductSold;
import com.sa.leanning.CarProject.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportColtroller {
    private final  ReportService reportService;
   
     @GetMapping("/{startDate}/{endDate}")
     public ResponseEntity<?> getReport(
             @PathVariable LocalDate startDate,
             @PathVariable LocalDate endDate
     ) {
    	 
     List<ProductReportDto> report = reportService.getReport(startDate, endDate)    ;	 
         return ResponseEntity.ok(report);
     }
    
     
     @GetMapping("expense/{startDate}/{endDate}")
     public ResponseEntity<?> getExpeseReport(
             @PathVariable LocalDate startDate,
             @PathVariable LocalDate endDate
     ) {
    	 
  List<ExpenseReportDto> expenseReport = reportService.getExpenseReport(startDate, endDate)    ;	 
         return ResponseEntity.ok(expenseReport);
     }
   
    
     
}
