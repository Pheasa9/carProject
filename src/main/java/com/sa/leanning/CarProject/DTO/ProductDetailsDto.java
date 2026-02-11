package com.sa.leanning.CarProject.DTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDetailsDto {

    // product identity
    private Long productId;
    private String productName;
    private String model;
    private String color;
    private String brand; 
    // pricing & stock (from Product)
    private BigDecimal salePrice;
    private Integer availableUnit;

    // detail fields (from ProductDetails)
    private BigDecimal discountPrice;
    private String description;
    private String storage;
    private String ram;
    private String processor;

    // media
    private String imagePath;
}
