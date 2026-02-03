package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sa.leanning.CarProject.Entities.Product;

import lombok.Data;

@Data
public class ProductImportDto {
     
	private Long productId;
	private LocalDateTime importDate;
	private Integer importUnit;
	private BigDecimal pricePerUnit;
	
	
}
