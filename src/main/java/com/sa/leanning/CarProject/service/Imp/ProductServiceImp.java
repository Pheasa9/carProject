package com.sa.leanning.CarProject.service.Imp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.ProductByNameDto;
import com.sa.leanning.CarProject.DTO.ProductImportDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;
import com.sa.leanning.CarProject.Mapper.ProductMapper;
import com.sa.leanning.CarProject.Util.ExcelUtils;
import com.sa.leanning.CarProject.repository.ProductImportHistoryRepository;
import com.sa.leanning.CarProject.repository.ProductRepository;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.spe.ProductFilter;
import com.sa.leanning.CarProject.spe.ProductSpecification;

import io.jsonwebtoken.io.IOException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImportHistoryRepository productImportHistoryRepository;
    private final ProductMapper productMapper;

    // ---------------- CREATE -------------------

    @Transactional
    @Override
    public Product create(Product product) {

        Long modelId = product.getModel().getId();
        Long colorId = product.getColor().getId();

        // Check if model + color already exist
        productRepository.findByModelAndColor(modelId, colorId)
                .ifPresent(existing -> {
                    throw new ApiException(
                            HttpStatus.BAD_REQUEST,
                            "Product with model '" + existing.getModel().getName() +
                            "' and color '" + existing.getColor().getName() +
                            "' already exists."
                    );
                });

        product.setName(buildProductName(product));

        return productRepository.save(product);
    }

    @Override
    public String buildProductName(Product product) {
        if (product.getModel() != null && product.getColor() != null) {
            return product.getModel().getName() + " " + product.getColor().getName();
        }
        return "Unknown Product";
    }

    // ---------------- GET ONE -------------------

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "Product with id %d was not found".formatted(id)
                ));
    }

 

    @Transactional
    @Override
    public void importProduct(ProductImportDto productImportDto) {

        Product product = getById(productImportDto.getProductId());

        int current = (product.getAvailableUnit() == null) ? 0 : product.getAvailableUnit();
        int newAvailable = current + productImportDto.getImportUnit();

        product.setAvailableUnit(newAvailable);

       
        ProductImportHistory history =
                productMapper.toProductImportHistory(productImportDto, product);

        productImportHistoryRepository.save(history);
    }


 
    @Override
    public void setPrice(Long id, BigDecimal price) {

        Product product = getById(id);
        product.setSalePrice(price);
        productRepository.save(product);
    }

	@Override
	public List<Product> getAllPrroductsById(List<Long> productIds) {
		List<Product> products = productRepository.findAllById(productIds);
		if(products.size() != productIds.size() ) {
			List<Long> idProductFound = products.stream().map(Product::getId).toList();
			List<Long> idProductNotFound = productIds.stream()
			.filter(id -> !idProductFound.contains(id)).toList();      
			throw new ApiException(HttpStatus.BAD_REQUEST, "Product id(s) %s is not found"
					.formatted(idProductNotFound));
		}
		
		
		return products;
	}

	
	
	   
	@Override
	public Map<Long, Product> getAllPrroductsByIdMap(List<Long> idProducts) {
		List<Product> products = getAllPrroductsById(idProducts);
		Map<Long, Product> productMap = products.stream()
		                                        .collect(Collectors
		                                        .toMap(Product::getId, p -> p));
		return productMap;
	}

	@Override
	@Transactional
	public Map<Integer, String> uploadProducts(MultipartFile file) throws IOException {

	    Map<Integer, String> errors = new HashMap<>();

	    try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

	        // ❗ DO NOT rely on sheet name
	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rows = sheet.iterator();

	        // skip header
	        if (rows.hasNext()) {
	            rows.next();
	        }

	        while (rows.hasNext()) {
	            Row row = rows.next();

	            // ✅ FIX: skip empty / ghost rows
	            if (row == null) {
	                continue;
	            }

	            // if model_id cell is empty → skip row
	            Cell modelCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	            if (modelCell == null) {
	                continue;
	            }

	            int rowNo = row.getRowNum() + 1;

	            try {
	                Long modelId = ExcelUtils.getRequiredLong(row, 1, "Model ID");
	                Long colorId = ExcelUtils.getRequiredLong(row, 2, "Color ID");
	                Integer unit = ExcelUtils.getRequiredInt(row, 3, "Quantity");
	                BigDecimal price = ExcelUtils.getOptionalDecimal(row, 4);

	                Product product = getByModelAndColor(modelId, colorId);

	                if (product.getAvailableUnit() == null) {
	                    product.setAvailableUnit(0);
	                }

	                product.setAvailableUnit(
	                        product.getAvailableUnit() + unit
	                );

	                productRepository.save(product);

	                ProductImportHistory history = new ProductImportHistory();
	                history.setProduct(product);
	                history.setImportUnit(unit);
	                history.setPricePerUnit(price);
	                history.setImportDate(LocalDateTime.now());

	                productImportHistoryRepository.save(history);

	            } catch (Exception ex) {
	                errors.put(rowNo, ex.getMessage());
	            }
	        }

	    } catch (Exception e) {
	        throw new ApiException(
	                HttpStatus.INTERNAL_SERVER_ERROR,
	                "Cannot read Excel file"
	        );
	    }

	    return errors;
	}

	
	
	@Override
	public Product getByModelAndColor(Long modelId, Long colorId) {
		Product product = productRepository.findByModelAndColor(modelId, colorId).orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST
	
				, "Color id : %s and Model %d not exiting Prodict".formatted(modelId,colorId)));	
		if(product.getAvailableUnit() == null) {
			product.setAvailableUnit(0);
			
		}
		return product;
	}

	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<ProductByNameDto> getProductByBrandId(Long brandId) {
		List<Product> products = productRepository.findByModelBrandId(brandId);
		return products.stream().map(p -> {
	        ProductByNameDto dto = new ProductByNameDto();
	        dto.setIdProduct(p.getId());
	        dto.setName(p.getName());
	        dto.setColor(p.getColor().getName());
	        dto.setAvailableUnit(p.getAvailableUnit());
	        dto.setSalePrice(p.getSalePrice());
	        dto.setImagePath(p.getImagePath());
	        return dto;
	    }).collect(Collectors.toList());
	}

	@Override
	public List<ProductByNameDto> getProductsByBrandName(String brandName) {
		List<Product> products = productRepository.findByModel_Brand_NameIgnoreCase(brandName);
	    
	    return products.stream().map(p -> {
	        ProductByNameDto dto = new ProductByNameDto();
	        dto.setIdProduct(p.getId());
	        dto.setName(p.getName());
	        dto.setColor(p.getColor().getName());
	        dto.setAvailableUnit(p.getAvailableUnit());
	        dto.setSalePrice(p.getSalePrice());
	        dto.setImagePath(p.getImagePath());
	        return dto;
	    }).collect(Collectors.toList());
		
	}
	
	
	
	// In ProductServiceImp.java
	public List<ProductByNameDto> getProductsWithFilter(ProductFilter filter) {
	    ProductSpecification spec = new ProductSpecification(filter);
	    List<Product> products = productRepository.findAll(spec);
	    
	    return products.stream().map(p -> {
	        ProductByNameDto dto = new ProductByNameDto();
	        dto.setIdProduct(p.getId());
	        dto.setName(p.getName());
	        dto.setAvailableUnit(p.getAvailableUnit());
	        dto.setSalePrice(p.getSalePrice());
	        dto.setImagePath(p.getImagePath());
	        return dto;
	    }).toList();
	}

}
