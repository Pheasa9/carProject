package com.sa.leanning.CarProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sa.leanning.CarProject.Entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {
	
	 @Query("SELECT p FROM Product p WHERE p.model.id = :modelId AND p.color.id = :colorId")
     Optional<Product> findByModelAndColor(Long modelId, Long colorId);
	 
	 List<Product> findByModelBrandId(Long brandId);
	 List<Product> findByModel_Brand_NameIgnoreCase(String brandName);
	

	
}
