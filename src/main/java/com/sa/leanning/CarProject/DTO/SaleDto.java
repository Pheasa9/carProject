package com.sa.leanning.CarProject.DTO;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaleDto {
	@NotEmpty
	private List<ProductSoldDTO> products;
	private LocalDateTime soldDate;
    
}
