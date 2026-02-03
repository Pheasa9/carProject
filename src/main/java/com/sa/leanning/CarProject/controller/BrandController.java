package com.sa.leanning.CarProject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sa.leanning.CarProject.DTO.BrandDto;
import com.sa.leanning.CarProject.DTO.ModelDto;
import com.sa.leanning.CarProject.DTO.PageDto;
import com.sa.leanning.CarProject.Entities.Brand;
import com.sa.leanning.CarProject.Mapper.BrandMapper;
import com.sa.leanning.CarProject.Mapper.ModelEntityMapper;
import com.sa.leanning.CarProject.service.BrandService;
import com.sa.leanning.CarProject.service.ModelService;

@RestController
@RequestMapping("brands")
@ControllerAdvice
public class BrandController {
	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandService serviceImp;
	@Autowired
	private ModelEntityMapper modelMapper;
	@Autowired
	private ModelService modelService;
    @PostMapping
	public ResponseEntity<?> create(@RequestBody BrandDto brandDto) {
		Brand brand = BrandMapper.INSTANCE.toBrand(brandDto);
		brand = serviceImp.createBrand(brand);

		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDto(brand));
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getOneBrand(@PathVariable Long id) {
		Brand brand = serviceImp.getById(id);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDto(brand));
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandDto brandUpdate) {
		Brand upDateBrand = serviceImp.upDateBrand(id, brandUpdate);

		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDto(upDateBrand));

	}

	@GetMapping("filter")
	public ResponseEntity<?> finByName(@RequestParam("name") String name) {
		return ResponseEntity
				.ok(serviceImp.getByName(name).stream().map(brand -> BrandMapper.INSTANCE.toBrandDto(brand))

				);

	}

	@GetMapping()
	public ResponseEntity<?> getBrands(@RequestParam Map<String, String> param) {

		Page<Brand> page = serviceImp.getBrands(param);
		PageDto pageDto = new PageDto(page);
//	        

		return ResponseEntity.ok(pageDto);

	}

	@GetMapping("{id}/models")
	public ResponseEntity<?> getModelByBrand(@PathVariable("id") Long brandId) {
		List<ModelDto> listModel = modelService.getModelByBrandId(brandId).stream().map(modelMapper::toModelDto)
				.toList();

		return ResponseEntity.ok(listModel);

	}

	@GetMapping("filter/model")
	public ResponseEntity<?> finByBrandName(@RequestParam("name") String name) {
		List<ModelDto> listModel = modelService.getModelByBrandName(name).stream().map(modelMapper::toModelDto)
				.toList();
		return ResponseEntity.ok(listModel);
	}

}
