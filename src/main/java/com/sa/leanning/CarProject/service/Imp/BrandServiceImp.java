package com.sa.leanning.CarProject.service.Imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.ApiException.ApiException;
import com.sa.leanning.CarProject.DTO.BrandDto;
import com.sa.leanning.CarProject.Entities.Brand;
import com.sa.leanning.CarProject.Util.PageUtil;
import com.sa.leanning.CarProject.repository.BrandRepository;
import com.sa.leanning.CarProject.service.BrandService;
import com.sa.leanning.CarProject.spe.BrandFilter;
import com.sa.leanning.CarProject.spe.BrandSpecification;

@Service
public class BrandServiceImp implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                            HttpStatus.NOT_FOUND,
                            "Id = %d is not found".formatted(id)
                        )
                );
    }

    @Override
    public Brand upDateBrand(Long id, BrandDto brandUpdate) {
        Brand brand = getById(id);
        brand.setName(brandUpdate.getName());
        // If your BrandDto has an imagePath, update it here too:
        // brand.setImagePath(brandUpdate.getImagePath());
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Page<Brand> getBrands(Map<String, String> param) {
        // 1. Initialize the filter object
        BrandFilter brandFilter = new BrandFilter();

        // 2. Map incoming parameters to the Filter object
        if (param.containsKey("name")) {
            brandFilter.setName(param.get("name"));
        }
        
        if (param.containsKey("id")) {
            try {
                brandFilter.setId(Integer.parseInt(param.get("id")));
            } catch (NumberFormatException e) {
                // Ignore invalid ID formats gracefully
            }
        }

        // 3. Create the Specification with the POPULATED filter
        BrandSpecification brandSpecification = new BrandSpecification(brandFilter);

        // 4. Handle Pagination
        int pageLimit = PageUtil.DEFAULT_PAGE_SIZE;
        if (param.containsKey(PageUtil.PAGE_LIMIT)) {
            try {
                pageLimit = Integer.parseInt(param.get(PageUtil.PAGE_LIMIT));
            } catch (NumberFormatException e) {
                pageLimit = PageUtil.DEFAULT_PAGE_SIZE;
            }
        }

        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (param.containsKey(PageUtil.PAGE_NUMBER)) {
            try {
                pageNumber = Integer.parseInt(param.get(PageUtil.PAGE_NUMBER));
            } catch (NumberFormatException e) {
                pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
            }
        }

        // 5. Build Pageable and Execute Query
        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);
       
        // This requires BrandRepository to extend JpaSpecificationExecutor
        return brandRepository.findAll(new BrandSpecification(brandFilter), pageable);
        
    }
}