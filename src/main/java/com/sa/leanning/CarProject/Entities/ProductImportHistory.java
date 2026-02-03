package com.sa.leanning.CarProject.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="products_import_histories")
public class ProductImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id")
    private Long id;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "import_unit")   
    private Integer importUnit;

    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;
}
