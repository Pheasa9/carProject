package com.sa.leanning.CarProject.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.sa.leanning.CarProject.DTO.BrandDto;
import com.sa.leanning.CarProject.Entities.Brand;
import com.sa.leanning.CarProject.repository.BrandRepository;

public interface BrandService {
     Brand createBrand (Brand brand);
     Brand getById(Long id);
     Brand upDateBrand (Long id , BrandDto brandUpdate);
     List<Brand> getByName(String name);
     Page<Brand> getBrands(Map<String, String> param);
     
} 
