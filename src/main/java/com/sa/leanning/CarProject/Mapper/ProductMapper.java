package com.sa.leanning.CarProject.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sa.leanning.CarProject.DTO.ProductDto;
import com.sa.leanning.CarProject.DTO.ProductImportDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;
import com.sa.leanning.CarProject.service.ColorService;
import com.sa.leanning.CarProject.service.ModelService;

@Mapper(componentModel = "spring", uses = {ModelService.class, ColorService.class})
public interface ProductMapper {

    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "color" , source = "colorId")
    Product toProduct(ProductDto dto);
     
    @Mapping(target = "product",source = "product")
    @Mapping(target = "id" , ignore = true)
    ProductImportHistory toProductImportHistory(ProductImportDto productImportDto,Product product);
    
    
    
    }
