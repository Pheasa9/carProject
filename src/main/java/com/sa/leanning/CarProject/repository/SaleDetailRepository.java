package com.sa.leanning.CarProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sa.leanning.CarProject.Entities.SaleDetail;
import com.sa.leanning.CarProject.projection.ProductSold;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> , JpaSpecificationExecutor<SaleDetail> {
	List<SaleDetail> findBySaleId(Long id);
	@Query(value = "SELECT p.id AS productId, " +
            "p.name AS productName, " +
            "SUM(sd.unit) AS unit, " +
            "SUM(sd.unit * sd.amount) AS totalAmount " + 
            "FROM sale_details sd " +
            "INNER JOIN sales s ON sd.sale_id = s.sale_id " +
            "INNER JOIN products p ON p.id = sd.product_id " +
            "WHERE s.sold_date::date >= :startDate " +
            "AND s.sold_date::date <= :endDate " +
            "GROUP BY p.id, p.name", nativeQuery = true)
	  List<ProductSold> findProductSold(LocalDate startDate , LocalDate endDate);
	
}
