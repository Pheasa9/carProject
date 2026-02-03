package com.sa.leanning.CarProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.ColorDto;
import com.sa.leanning.CarProject.Entities.Color;
import com.sa.leanning.CarProject.Mapper.ColorMapper;
import com.sa.leanning.CarProject.service.ColorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
public class ColorController {
	@Autowired
	private final ColorMapper colorMapper;
	private final ColorService colorService;
	   
	   @GetMapping("{id}")
	   public ResponseEntity<?> getOneColor(@PathVariable Long id){
		   
		  return ResponseEntity.ok(colorService.getById(id));
	   }
	   @PostMapping
	   public ResponseEntity<?> createColor(@RequestBody ColorDto colorDto){
			   Color color = colorService.Create(colorMapper.toColor(colorDto));
			   
	    	  return ResponseEntity.ok(colorMapper.toColorDto(color));
	      }
}
