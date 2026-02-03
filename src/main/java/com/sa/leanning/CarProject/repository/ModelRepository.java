package com.sa.leanning.CarProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.leanning.CarProject.Entities.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
      List<Model> findByBrandId(Long brandId);
      List<Model> findByBrand_Name(String brandName);
}
