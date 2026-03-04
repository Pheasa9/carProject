package com.sa.leanning.CarProject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;   // ✅ correct
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

    // ✅ CREATE PRODUCT + UPLOAD IMAGE (multipart/form-data)
    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProductWithImage(
            @RequestParam("modelId") Long modelId,
            @RequestParam("colorId") Long colorId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {

        ProductDto dto = new ProductDto();
        dto.setModelId(modelId);
        dto.setColorId(colorId);

        Product product = productMapper.toProduct(dto);

        // ✅ if file provided → save it and set imagePath
        if (file != null && !file.isEmpty()) {
            String fileName = productService.saveProductImage(file);
            product.setImagePath(fileName);
        }

        Product saved = productService.create(product);
        return ResponseEntity.ok(saved);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.create(productMapper.toProduct(productDto));
        return ResponseEntity.ok(product);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable("id") Long productId) {
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
    public ResponseEntity<?> importProduct(@RequestBody ProductImportDto productImportDto) {
        productService.importProduct(productImportDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/price")
    public ResponseEntity<?> setSalePrice(@PathVariable("id") Long ProductID, @RequestBody SetPriceDto setPriceDto) {
        productService.setPrice(ProductID, setPriceDto.getPrice());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/uploads")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        Map<Integer, String> products = productService.uploadProducts(file);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/getByColorAndModel")
    public ResponseEntity<?> getProductByModelAndColor(@RequestBody ProductColorAndModelDto dto) {
        Product product = productService.getByModelAndColor(dto.getModelId(), dto.getColorId());
        return ResponseEntity.ok(product);
    }
}
