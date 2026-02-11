package com.sa.leanning.CarProject.service;

import com.sa.leanning.CarProject.DTO.ProductDetailsDto;

public interface ProductDetailsService {
	ProductDetailsDto createProductDetail(ProductDetailsDto detailsDto);
	ProductDetailsDto getByProductId(Long productId);
}
