package com.sa.leanning.CarProject.service.Imp;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.Entities.Color;
import com.sa.leanning.CarProject.repository.ColorRepository;
import com.sa.leanning.CarProject.service.ColorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ColorServiceImp  implements ColorService{
     
	private final ColorRepository colorRepository;
	
	
	
	@Override
	public Color getById(Long id) {
		
		return colorRepository.findById(id).orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND,
				"Color with id = %d not found".formatted(id)));
	}



	@Override
	public Color Create(Color color) {
		
		return colorRepository.save(color);
	}
    
}
