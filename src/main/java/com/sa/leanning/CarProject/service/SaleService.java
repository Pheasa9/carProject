package com.sa.leanning.CarProject.service;

import com.sa.leanning.CarProject.DTO.SaleDto;
import com.sa.leanning.CarProject.Entities.Sale;

public interface SaleService {
      void sell(SaleDto saleDto);
	  Sale getById(Long id);
	  void saleCancel(Long saleId);
	
	
	
}
