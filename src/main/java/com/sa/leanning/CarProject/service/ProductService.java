package com.sa.leanning.CarProject.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sa.leanning.CarProject.DTO.ProductByNameDto;
import com.sa.leanning.CarProject.DTO.ProductImportDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.spe.ProductFilter;


public interface ProductService {
     Product create(Product product);
     String buildProductName(Product product);
     void importProduct(ProductImportDto productImportDto) ; 
     Product getById(Long id);
     void setPrice( Long id , BigDecimal price);
    
     List<Product> getAllPrroductsById(List<Long> productId);
     Map<Long, Product> getAllPrroductsByIdMap(List<Long> idProduct);
     Map<Integer, String> uploadProducts(MultipartFile file);
     Product getByModelAndColor(Long modelId , Long colorId);
     List<Product> getProducts();
     
     
     List<ProductByNameDto> getProductByBrandId(Long brandId);
     List<ProductByNameDto> getProductsByBrandName(String brandName);
     List<ProductByNameDto> getProductsWithFilter(ProductFilter filter);
    	 
      void delete(Long id);
      
     
}
