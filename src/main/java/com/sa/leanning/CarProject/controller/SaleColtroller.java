package com.sa.leanning.CarProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sa.leanning.CarProject.DTO.SaleDto;
import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Mapper.ProductMapper;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleColtroller {
     private final ProductService productService;
     private final ProductMapper productMapper;
     private final SaleService saleService;
     @PostMapping
     public ResponseEntity<?> createSale(@RequestBody SaleDto saleDto) {       
         saleService.sell(saleDto); 
         return ResponseEntity.ok().build();

     }
     @GetMapping("/{id}")
     public ResponseEntity<?> getSale(@PathVariable("id") Long saleId){
    	 Sale sale = saleService.getById(saleId);
    	 
    	 
    	  return ResponseEntity.ok(sale);
     }
     @PutMapping("/{saleId}/cancel")
      public ResponseEntity<?> cancelSale(@PathVariable Long saleId){
    	  
    	  saleService.saleCancel(saleId);
    	  
    	  return ResponseEntity.ok().build();
    	  
      }
     
}
