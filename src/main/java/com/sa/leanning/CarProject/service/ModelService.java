package com.sa.leanning.CarProject.service;

import java.util.List;

import com.sa.leanning.CarProject.Entities.Model;

public interface ModelService {
     Model save(Model model);
     List<Model> getModelByBrandId(Long integer);
     List<Model> getModelByBrandName(String BrandName);
     Model getById(Long id);
}
