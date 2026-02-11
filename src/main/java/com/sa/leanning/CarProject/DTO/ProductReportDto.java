package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductReportDto {
	
	private Long productId;
	private List<Long> saleIds;
	private String productName;
	private Integer totalUnit;
	private BigDecimal totalAmount;
}
