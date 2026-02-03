package com.sa.leanning.CarProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;

public interface ProductImportHistoryRepository extends JpaRepository<ProductImportHistory, Long> 
                                , JpaSpecificationExecutor<ProductImportHistory> {
	
	
	
	    
}
