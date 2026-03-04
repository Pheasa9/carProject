package com.sa.leanning.CarProject.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleReceiptResponseDto {

    private Long saleId;
    private LocalDateTime soldDate;
    private List<SaleReceiptItemDto> items;
    private BigDecimal total;
}