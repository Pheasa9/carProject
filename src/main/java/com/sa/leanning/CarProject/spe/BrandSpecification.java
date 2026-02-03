package com.sa.leanning.CarProject.spe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.Brand;
import com.sa.leanning.CarProject.Entities.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandSpecification implements Specification<Brand> {

    private final BrandFilter brandfilter;

    @Override
    public Predicate toPredicate(Root<Brand> product, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 1. Filter by Product Name (Partial Match)
        if (brandfilter.getName() != null && !brandfilter.getName().isEmpty()) {
            Predicate namePredicate = cb.like(cb.lower(product.get("name")), 
                                       "%" + brandfilter.getName().toLowerCase() + "%");
            predicates.add(namePredicate);
        }

        // 2. Filter by Brand ID (Exact Match)
        // Logic: Product -> Model -> Brand -> id
        if (brandfilter.getId() != null) {
            Predicate brandIdPredicate = cb.equal(product.get("model").get("brand").get("id"), 
            		brandfilter.getId());
            predicates.add(brandIdPredicate);
        }

        // 3. Filter by Brand Name (Partial Match)
        // Logic: Product -> Model -> Brand -> name
        if (brandfilter.getName() != null && !brandfilter.getName().isEmpty()) {
            Predicate brandNamePredicate = cb.like(cb.lower(product.get("model").get("brand").get("name")), 
                                            "%" + brandfilter.getName().toLowerCase() + "%");
            predicates.add(brandNamePredicate);
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}