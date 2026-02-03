package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductByNameDto {
    
	private String name;
	private Long idProduct;
	private String color;
	private BigDecimal salePrice;
	private String imagePath;	
	private int availableUnit ;
	
	 
	  
	  
	  
	
	  
	
}
