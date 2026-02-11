package com.sa.leanning.CarProject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sa.leanning.CarProject.DTO.ProductByNameDto;
import com.sa.leanning.CarProject.DTO.ProductColorAndModelDto;
import com.sa.leanning.CarProject.DTO.ProductDto;
import com.sa.leanning.CarProject.DTO.ProductImportDto;
import com.sa.leanning.CarProject.DTO.SetPriceDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Mapper.ProductMapper;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.spe.ProductFilter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
          private final ProductService productService;
          private final ProductMapper productMapper;
          
          
          @PostMapping
          public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        	   Product product = productService.create(productMapper.toProduct(productDto));
     	    return ResponseEntity.ok(product);
        	  
          }
          @GetMapping("{id}")
          public ResponseEntity<?> getOneProduct(@PathVariable("id") Long productId ){
        	  
        	  return ResponseEntity.ok(productService.getById(productId));

          }
          
          @GetMapping("/filter")
          public ResponseEntity<List<ProductByNameDto>> getProductsFiltered(ProductFilter filter) {
              
              List<ProductByNameDto> products = productService.getProductsWithFilter(filter);
              
              if (products.isEmpty()) {
                  return ResponseEntity.noContent().build();
              }
              
              return ResponseEntity.ok(products);
          }

      	
          
          
          
          
          @GetMapping
          public ResponseEntity<List<Product>> getProducts() {
              return ResponseEntity.ok(productService.getProducts());
          }

          
          @PostMapping("importProduct")
          public ResponseEntity<?> importProduct(@RequestBody ProductImportDto productImportDto){
        	  productService.importProduct(productImportDto);
        	  return ResponseEntity.ok().build();
          }
          
          @PostMapping("{id}/price")
          public ResponseEntity<?> setSalePrice(@PathVariable("id") Long ProductID ,@RequestBody SetPriceDto setPriceDto){
        	  productService.setPrice(ProductID, setPriceDto.getPrice()) ;
        	  
        	  return ResponseEntity.ok().build();
          }
          
          @PostMapping("/uploads")
          public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
             Map<Integer, String> products = productService.uploadProducts(file);
              return ResponseEntity.ok(products);
          }
          
          @PostMapping("/getByColorAndModel")
          public ResponseEntity<?> getProductByModelAndColor(@RequestBody ProductColorAndModelDto idColorandBroductt ) {
                Product product = productService.getByModelAndColor(idColorandBroductt.getModelId(), idColorandBroductt.getColorId());
                return ResponseEntity.ok(product);
          }
}
