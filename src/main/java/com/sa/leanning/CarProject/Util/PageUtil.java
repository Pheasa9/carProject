package com.sa.leanning.CarProject.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageUtil {
	int DEFAULT_PAGE_SIZE=100;
	int DEFAULT_PAGE_NUMBER = 1;
	String PAGE_LIMIT = "_limit";
	String  PAGE_NUMBER = "_page";
	static Pageable getPageable(int pageNumber ,int pageSize) {
		
		if(pageNumber <1 ) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		if(pageSize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize-1);
		
		
		return pageable;
	}
}
