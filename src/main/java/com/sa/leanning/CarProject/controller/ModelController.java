package com.sa.leanning.CarProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.ModelDto;
import com.sa.leanning.CarProject.Entities.Model;
import com.sa.leanning.CarProject.Mapper.ModelEntityMapper;
import com.sa.leanning.CarProject.service.ModelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

	private final ModelService service;
	private final ModelEntityMapper modelMapper;

	@PostMapping
	public ResponseEntity<?> createModel(@RequestBody ModelDto modelDto) {

		Model model = modelMapper.toModel(modelDto);
		model = service.save(model);
         modelDto = modelMapper.toModelDto(model);
		return ResponseEntity.ok(modelDto);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getOneModel(@PathVariable Long id){
		return ResponseEntity.ok(modelMapper.toModelDto(service.getById(id)));
		
	}

}
