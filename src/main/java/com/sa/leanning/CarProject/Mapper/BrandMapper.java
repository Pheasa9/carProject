

package com.sa.leanning.CarProject.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sa.leanning.CarProject.DTO.BrandDto;
import com.sa.leanning.CarProject.Entities.Brand;

@Mapper
public interface BrandMapper {
	   BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
       Brand toBrand (BrandDto brandDto);
       BrandDto toBrandDto (Brand brand);
}
