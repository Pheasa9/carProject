package com.sa.leanning.CarProject.DTO;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageDto {
      private List<?> list;
      private PaginationDto pagination;
      
     
      
      
  	public PageDto(Page<?> page) {
  	    this.list = page.getContent();
  	    this.pagination = PaginationDto.builder()
  	            .first(page.isFirst())                 // ✅ isFirst → true if this is the first page
  	            .last(page.isLast())                   // ✅ isLast → true if this is the last page
  	            .pageNumber(page.getNumber()+1)          // ✅ current page index (0-based)
  	            .pageSize(page.getSize())              // ✅ number of items per page
  	            .totalElements(page.getTotalElements())// ✅ total records in DB
  	            .numberOfElements(page.getNumberOfElements()) // ✅ number of elements in current page
  	            .build();
  	}

      
}
