package com.sa.leanning.CarProject.Mapper;

import org.mapstruct.*;
import com.sa.leanning.CarProject.DTO.ProductDetailsDto;
import com.sa.leanning.CarProject.Entities.ProductDetails;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductDetailsMapper {

    @Mapping(target = "product", ignore = true)
    ProductDetails toEntity(ProductDetailsDto dto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.salePrice", target = "salePrice")
    @Mapping(source = "product.availableUnit", target = "availableUnit")
    @Mapping(source = "product.imagePath", target = "imagePath")

    @Mapping(source = "product.model.brand.name", target = "brand") // ✅ brand
    @Mapping(source = "product.model.name", target = "model")
    @Mapping(source = "product.color.name", target = "color")
    ProductDetailsDto toDto(ProductDetails entity);
}
