package com.sa.leanning.CarProject.service.Imp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.ProductSoldDTO;
import com.sa.leanning.CarProject.DTO.SaleDto;
import com.sa.leanning.CarProject.DTO.SaleReceiptItemDto;
import com.sa.leanning.CarProject.DTO.SaleReceiptResponseDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Entities.SaleDetail;
import com.sa.leanning.CarProject.repository.ProductRepository;
import com.sa.leanning.CarProject.repository.SaleDetailRepository;
import com.sa.leanning.CarProject.repository.SaleRepository;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.service.SaleService;
import com.sa.leanning.CarProject.service.TelegramService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SaleServiceImp implements SaleService {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final TelegramService telegramService;
    @Override
    @Transactional
    public SaleReceiptResponseDto sell(SaleDto saleDto) {

        Sale sale = new Sale();
        sale.setStatus(true);
        sale.setSoldDate(
                saleDto.getSoldDate() != null ? saleDto.getSoldDate() : LocalDateTime.now()
        );

        Sale savedSale = saleRepository.save(sale);

        List<Long> productIds = saleDto.getProducts().stream()
                .map(ProductSoldDTO::getProductId)
                .toList();

        Map<Long, Product> productMap =
                productService.getAllPrroductsByIdMap(productIds);

        BigDecimal total = BigDecimal.ZERO;

        List<SaleReceiptItemDto> receiptItems = saleDto.getProducts().stream()
                .map(sold -> {

                    Product product = productMap.get(sold.getProductId());

                    if (product.getAvailableUnit() < sold.getUnit()) {
                        throw new ApiException(HttpStatus.BAD_REQUEST,
                                "Not enough stock for product: " + product.getName());
                    }

                    product.setAvailableUnit(
                            product.getAvailableUnit() - sold.getUnit()
                    );

                    BigDecimal lineTotal =
                            product.getSalePrice().multiply(BigDecimal.valueOf(sold.getUnit()));

                    SaleDetail detail = new SaleDetail();
                    detail.setSale(savedSale);
                    detail.setProduct(product);
                    detail.setUnit(sold.getUnit());
                    detail.setAmount(lineTotal);

                    saleDetailRepository.save(detail);

                    SaleReceiptItemDto item = new SaleReceiptItemDto();
                    item.setProductId(product.getId());
                    item.setProductName(product.getName());
                    item.setBrand(
                    	    product.getModel() != null &&
                    	    product.getModel().getBrand() != null
                    	        ? product.getModel().getBrand().getName()
                    	        : "—"
                    	);                    item.setColor(product.getColor() != null ? product.getColor().getName() : "—");
                    item.setUnitPrice(product.getSalePrice());
                    item.setQty(sold.getUnit());
                    item.setLineTotal(lineTotal);

                    return item;
                })
                .toList();

        productRepository.saveAll(productMap.values());

        for (SaleReceiptItemDto item : receiptItems) {
            total = total.add(item.getLineTotal());
        }

        // Build receipt response
        SaleReceiptResponseDto receipt = new SaleReceiptResponseDto();
        receipt.setSaleId(savedSale.getId());
        receipt.setSoldDate(savedSale.getSoldDate());
        receipt.setItems(receiptItems);
        receipt.setTotal(total);

        // 🔥 SEND TO TELEGRAM
        telegramService.sendMessage(buildTelegramMessage(receipt));

        return receipt;
    }

    
    private String buildTelegramMessage(SaleReceiptResponseDto r) {

        StringBuilder sb = new StringBuilder();

        sb.append("<b>🧾 RECEIPT</b>\n");
        sb.append("Sale ID: <b>").append(r.getSaleId()).append("</b>\n");
        sb.append("Date: ").append(r.getSoldDate()).append("\n\n");

        sb.append("<b>Items:</b>\n");

        for (SaleReceiptItemDto it : r.getItems()) {
            sb.append("• <b>").append(it.getProductName()).append("</b>\n");
            sb.append("  ").append(it.getQty())
                    .append(" x $").append(it.getUnitPrice())
                    .append(" = <b>$").append(it.getLineTotal()).append("</b>\n\n");
        }

        sb.append("<b>Total: $").append(r.getTotal()).append("</b>\n");
        sb.append("✅ Thank you!");

        return sb.toString();
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