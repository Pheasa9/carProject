package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExpenseReportDto {
  private Long productId;
  private String productName;
  private Integer unit;
  private BigDecimal totalAmount;
  
  
  
	
}
