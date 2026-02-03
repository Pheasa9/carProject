package com.sa.leanning.CarProject.spe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.ProductImportHistory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductImportHistorySpecification implements Specification<ProductImportHistory> {
	private final ProductImportHistoryFilter filter;
	List<Predicate> predicates = new ArrayList<>();

	@Override
	public Predicate toPredicate(Root<ProductImportHistory> importHistory, CriteriaQuery<?> query, CriteriaBuilder cb) {

		if (Objects.nonNull(filter.getStartDate())) {
			Predicate start = cb.greaterThanOrEqualTo(importHistory.get("importDate"), filter.startDate.atStartOfDay());
			predicates.add(start);

		}
		if (Objects.nonNull(filter.getEndDate())) {

			Predicate endDate = cb.lessThanOrEqualTo(importHistory.get("importDate"), filter.endDate.atTime(23, 59, 59));
			predicates.add(endDate);

		}

		return cb.and(predicates.toArray(Predicate[]::new));
	}

}
