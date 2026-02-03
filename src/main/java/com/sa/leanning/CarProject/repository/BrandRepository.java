package com.sa.leanning.CarProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sa.leanning.CarProject.Entities.Brand;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> , JpaSpecificationExecutor<Brand>{
      List<Brand> findByName(String name);

	
}
