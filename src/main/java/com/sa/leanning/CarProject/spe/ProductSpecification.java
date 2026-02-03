package com.sa.leanning.CarProject.spe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private final ProductFilter productFilter;

 // ProductSpecification.java

    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 1. Filter by Brand Name (This matches what we send from Angular)
        // We use productFilter.getBrandName() assuming you add this field to your Filter class
        if (productFilter.getBrandName() != null && !productFilter.getBrandName().isEmpty()) {
            Predicate brandNamePredicate = cb.equal(
                cb.lower(product.get("model").get("brand").get("name")), 
                productFilter.getBrandName().toLowerCase()
            );
            predicates.add(brandNamePredicate);
        }

        // 2. Filter by Brand ID (Existing logic)
        if (productFilter.getBrandId() != null) {
            predicates.add(cb.equal(product.get("model").get("brand").get("id"), productFilter.getBrandId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}