package com.sa.leanning.CarProject.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaleReceiptItemDto {

    private Long productId;
    private String productName;
    private String brand;
    private String color;
    private BigDecimal unitPrice;
    private int qty;
    private BigDecimal lineTotal;
}