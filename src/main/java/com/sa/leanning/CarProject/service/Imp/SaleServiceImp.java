package com.sa.leanning.CarProject.service.Imp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.ProductSoldDTO;
import com.sa.leanning.CarProject.DTO.SaleDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Entities.SaleDetail;
import com.sa.leanning.CarProject.repository.ProductRepository;
import com.sa.leanning.CarProject.repository.SaleDetailRepository;
import com.sa.leanning.CarProject.repository.SaleRepository;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.service.SaleService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SaleServiceImp implements SaleService {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Override
    @Transactional
    public void sell(SaleDto saleDto) {

        // 1️⃣ Create sale header
        Sale sale = new Sale();
        sale.setStatus(true);
        sale.setSoldDate(
            saleDto.getSoldDate() != null ? saleDto.getSoldDate() : LocalDateTime.now()
        );

        Sale savedSale = saleRepository.save(sale); // use final variable

        // 2️⃣ Extract product IDs
        List<Long> productIds = saleDto.getProducts().stream()
                                       .map(ProductSoldDTO::getProductId)
                                       .toList();

        // 3️⃣ Load products from DB
        Map<Long, Product> productMap = productService.getAllPrroductsByIdMap(productIds);
        
        // 4️⃣ Check stock and deduct
        for (ProductSoldDTO sold : saleDto.getProducts()) {
            Product product = productMap.get(sold.getProductId());
            if (product.getAvailableUnit() < sold.getUnit()) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for product: " + product.getName());
            }
            product.setAvailableUnit(product.getAvailableUnit() - sold.getUnit());
        }

        // 5️⃣ Save updated products
        productRepository.saveAll(productMap.values());

        // 6️⃣ Create SaleDetail
        List<SaleDetail> details = saleDto.getProducts().stream()
                .map(sold -> {
                    Product product = productMap.get(sold.getProductId());
                    SaleDetail detail = new SaleDetail();
                    detail.setSale(savedSale); // use savedSale
                    detail.setProduct(product);
                    detail.setUnit(sold.getUnit());
                    detail.setAmount(product.getSalePrice());
                    
                    return detail;
                }).toList();

        saleDetailRepository.saveAll(details);
    }
    
    

	@Override
	public Sale getById(Long id) {
		 Sale sale = saleRepository.findById(id).orElseThrow(() -> new 
				                          ApiException(HttpStatus.BAD_REQUEST, 
				                        		  "Sale with id = %d not found".formatted(id)));	
		return sale;
	}



	 @Override
	    @Transactional  // Ensures all-or-nothing behavior
	    public void saleCancel(Long saleId) {

	        // 1️⃣ Fetch sale
	        Sale sale = saleRepository.findById(saleId)
	                .orElseThrow(() -> new RuntimeException("Sale not found with ID: " + saleId));

	        if (!sale.getStatus() ) {
	            throw new RuntimeException("Sale is already cancelled: " + saleId);
	        }
	       

	        sale.setStatus(false);
	        saleRepository.save(sale);

	        // 2️⃣ Fetch all sale details
	        List<SaleDetail> saleDetailList = saleDetailRepository.findBySaleId(saleId);

	        if (saleDetailList.isEmpty()) {
	            throw new RuntimeException("No sale details found for sale ID: " + saleId);
	        }

	        // 3️⃣ Get all products in one query
	        List<Long> productIds = saleDetailList.stream()
	                .map(sd -> sd.getProduct().getId())
	                .toList();

	        Map<Long, Product> productMap = productService.getAllPrroductsByIdMap(productIds);

	        // 4️⃣ Restock products
	        saleDetailList.forEach(sd -> {
	            Product product = productMap.get(sd.getProduct().getId());
	            if (product == null) {
	                throw new RuntimeException("Product not found for ID: " + sd.getProduct().getId());
	            }

	            product.setAvailableUnit(product.getAvailableUnit() + sd.getUnit());
	        });

	        // 5️⃣ Batch save all products (efficient)
	        productRepository.saveAll(productMap.values());
	    }

}
