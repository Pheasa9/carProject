package com.sa.leanning.CarProject.spe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Entities.SaleDetail;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportSpecification implements Specification<SaleDetail> {

    private final ReportFilter filter;

    @Override
    public Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query, CriteriaBuilder cb) {

        // Join SaleDetail -> Sale (sale_details.sale_id -> sales.sale_id)
        Join<SaleDetail, Sale> sale = saleDetail.join("sale", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        // ✅ Filter ONLY sales with status = true
        // excludes false + null
        predicates.add(cb.isTrue(sale.get("status")));

        // ✅ Start Date (>= startDate 00:00:00)
        if (Objects.nonNull(filter.getStartDate())) {
            LocalDateTime start = filter.getStartDate().atStartOfDay();
            predicates.add(cb.greaterThanOrEqualTo(sale.get("soldDate"), start));
        }

        // ✅ End Date (<= endDate 23:59:59)
        if (Objects.nonNull(filter.getEndDate())) {
            LocalDateTime end = filter.getEndDate().atTime(23, 59, 59);
            predicates.add(cb.lessThanOrEqualTo(sale.get("soldDate"), end));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
