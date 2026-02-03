package com.sa.leanning.CarProject.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.ModelDto;
import com.sa.leanning.CarProject.Entities.Model;
import com.sa.leanning.CarProject.Mapper.ModelEntityMapper;
import com.sa.leanning.CarProject.repository.ModelRepository;
import com.sa.leanning.CarProject.service.ModelService;

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
   
	
	
	
}
