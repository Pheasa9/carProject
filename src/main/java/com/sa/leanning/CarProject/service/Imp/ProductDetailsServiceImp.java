package com.sa.leanning.CarProject.service.Imp;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.ProductDetailsDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductDetails;
import com.sa.leanning.CarProject.Mapper.ProductDetailsMapper;
import com.sa.leanning.CarProject.repository.ProductDetailsRepository;
import com.sa.leanning.CarProject.service.ProductDetailsService;
import com.sa.leanning.CarProject.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductDetailsServiceImp implements ProductDetailsService {

    private final ProductService productService;
    private final ProductDetailsRepository detailsRepository;
    private final ProductDetailsMapper mapper;

    @Override
    @Transactional
    public ProductDetailsDto createProductDetail(ProductDetailsDto detailsDto) {

        // 1) Get Product using existing service (reuse your validation)
        Product product = productService.getById(detailsDto.getProductId());

        // 2) Enforce one-to-one: only one ProductDetails per Product
        if (detailsRepository.existsByProduct_Id(product.getId())) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "ProductDetails already exists for product id: " + product.getId()
            );
        }

        // 3) Map DTO -> Entity (product ignored in mapper)
        ProductDetails productDetails = mapper.toEntity(detailsDto);

        // 4) Set relationship
        productDetails.setProduct(product);

        // 5) Save
        ProductDetails saved = detailsRepository.save(productDetails);

        // 6) Re-fetch with JOIN FETCH to avoid Lazy/N+1 when mapping to DTO
        ProductDetails full = detailsRepository.findByIdWithAll(saved.getId())
                .orElseThrow(() -> new ApiException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Cannot load saved ProductDetails with relations"
                ));

        // 7) Map Entity -> DTO
        return mapper.toDto(full);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailsDto getByProductId(Long productId) {

        ProductDetails details = detailsRepository.findByProductIdWithAll(productId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "ProductDetails not found for product id: " + productId
                ));

        return mapper.toDto(details);
    }
}
