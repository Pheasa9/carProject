package com.sa.leanning.CarProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sa.leanning.CarProject.Entities.ProductDetails;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {

    boolean existsByProduct_Id(Long productId);

    @Query("""
        SELECT pd
        FROM ProductDetails pd
        JOIN FETCH pd.product p
        JOIN FETCH p.model m
        JOIN FETCH m.brand b
        JOIN FETCH p.color c
        WHERE pd.id = :id
    """)
    Optional<ProductDetails> findByIdWithAll(@Param("id") Long id);

    @Query("""
        SELECT pd
        FROM ProductDetails pd
        JOIN FETCH pd.product p
        JOIN FETCH p.model m
        JOIN FETCH m.brand b
        JOIN FETCH p.color c
        WHERE p.id = :productId
    """)
    Optional<ProductDetails> findByProductIdWithAll(@Param("productId") Long productId);
    
    
    void deleteByProduct_Id(Long productId);
  

    
    
    
}
 