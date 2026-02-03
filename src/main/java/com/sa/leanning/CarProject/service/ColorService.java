package com.sa.leanning.CarProject.service;

import com.sa.leanning.CarProject.Entities.Color;

public interface ColorService {
       
	Color getById(Long id);
	Color Create(Color color);
	
}
