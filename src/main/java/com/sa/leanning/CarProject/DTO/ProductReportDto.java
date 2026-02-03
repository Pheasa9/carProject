package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProductReportDto {
	private Long productId;
	private String productName;
	private Integer totalUnit;
	private BigDecimal totalAmount;
}
