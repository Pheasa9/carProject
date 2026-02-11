package com.sa.leanning.CarProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sa.leanning.CarProject.DTO.ProductDetailsDto;
import com.sa.leanning.CarProject.service.ProductDetailsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/productDetails")
@AllArgsConstructor
public class ProductDetailsController {

    private final ProductDetailsService productDetailsService;

    // CREATE product details
    @PostMapping
    public ResponseEntity<ProductDetailsDto> createProductDetails(
            @RequestBody ProductDetailsDto detailsDto) {

        ProductDetailsDto created =
                productDetailsService.createProductDetail(detailsDto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailsDto> getByProductId(
            @PathVariable Long productId) {

        ProductDetailsDto dto =
                productDetailsService.getByProductId(productId);

        return ResponseEntity.ok(dto);
    }
}
