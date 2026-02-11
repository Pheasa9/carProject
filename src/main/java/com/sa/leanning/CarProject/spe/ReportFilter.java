package com.sa.leanning.CarProject.spe;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportFilter {
    LocalDate startDate;
    LocalDate endDate;
    private Boolean status; 
}
