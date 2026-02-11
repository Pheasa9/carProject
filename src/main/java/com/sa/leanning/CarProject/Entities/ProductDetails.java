package com.sa.leanning.CarProject.Entities;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    // extra detail fields (not in Product)
    @Column(length = 1000)
    private String description;

    // keep discount here (since Product has salePrice already)
    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    // extra specs (choose what matches your app)
    private String storage;
    private String ram;
    private String processor;

    
}
