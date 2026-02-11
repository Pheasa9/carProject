package com.sa.leanning.CarProject.service.Imp;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.Entities.Model;
import com.sa.leanning.CarProject.repository.ModelRepository;
import com.sa.leanning.CarProject.service.ModelService;
import com.sa.leanning.CarProject.spe.ModelFilter;
import com.sa.leanning.CarProject.spe.ModelSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModelServiceImp implements ModelService {

	private final ModelRepository modelRepository;
	
	@Override
	public Model save(Model model) {
		
		return modelRepository.save(model);
	}

	@Override
	public List<Model> getModelByBrandId(Long brandId) {
       

		return modelRepository.findByBrandId(brandId);
	}

	@Override
	public List<Model> getModelByBrandName(String BrandName) {
		
		return modelRepository.findByBrand_Name(BrandName);
	}

	@Override
	public Model getById(Long id) {
		
		return modelRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND ,
				"Model with id %d is not found".formatted(id)));
	}
   
	@Override
	public Page<Model> getModels(Map<String, String> params) {
	    ModelFilter filter = new ModelFilter(params);
	    Pageable pageable = PageRequest.of(
	        filter.getPage(),
	        filter.getSize()
	    );

	    Specification<Model> spec = new ModelSpecification(filter);

	    return modelRepository.findAll(spec, pageable);
	}

	
}
