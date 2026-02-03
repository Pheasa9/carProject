package com.sa.leanning.CarProject.Entities;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"model_id", "color_id"})
    }
)
public class Product { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

   
    private String imagePath;

    @Column(name = "available_unit", nullable = true)
    private Integer availableUnit;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = true)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = true)
    private Color color;

    @Column(name = "sale_price")
    private BigDecimal salePrice;
}
