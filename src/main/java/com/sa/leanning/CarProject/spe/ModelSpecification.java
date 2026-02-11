package com.sa.leanning.CarProject.spe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sa.leanning.CarProject.Entities.Model;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ModelSpecification implements Specification<Model> {

    private final ModelFilter filter;

    

    @Override
    public Predicate toPredicate(
        Root<Model> root,
        CriteriaQuery<?> query,
        CriteriaBuilder cb
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getBrandId() != null) {
            predicates.add(
                cb.equal(root.get("brand").get("id"), filter.getBrandId())
            );
        }

        if (filter.getName() != null && !filter.getName().isBlank()) {
            predicates.add(
                cb.like(
                    cb.lower(root.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"
                )
            );
        }

        // 🔥 THIS LINE IS THE KEY
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
