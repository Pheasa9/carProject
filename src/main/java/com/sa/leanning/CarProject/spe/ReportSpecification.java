package com.sa.leanning.CarProject.spe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Entities.SaleDetail;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ReportSpecification implements Specification<SaleDetail>{
   private final ReportFilter filter;

@Override
public Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query, CriteriaBuilder cb) {
	Join<SaleDetail, Sale> sale = saleDetail.join("sale");
	
	List<Predicate> predicates = new ArrayList<>();
	if(Objects.nonNull(filter.startDate)) {
		Predicate startDate = cb.greaterThanOrEqualTo(sale.get("soldDate"), filter.startDate.atStartOfDay());
		predicates.add(startDate);
	}
	if(Objects.nonNull(filter.endDate)) {
		Predicate endDate = cb.lessThanOrEqualTo(sale.get("soldDate"), filter.getEndDate().atTime(23, 59, 59));
		predicates.add(endDate);
	}
	return cb.and(predicates.toArray(Predicate[]::new));
}
   
   
   
   
	
	
	
}
