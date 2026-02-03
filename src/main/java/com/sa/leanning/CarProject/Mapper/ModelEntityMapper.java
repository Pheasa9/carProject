package com.sa.leanning.CarProject.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sa.leanning.CarProject.DTO.ModelDto;
import com.sa.leanning.CarProject.Entities.Model;
import com.sa.leanning.CarProject.service.BrandService;

@Mapper(componentModel = "spring",uses = {BrandService.class})
public interface ModelEntityMapper {
     ModelEntityMapper INSTANCE = Mappers.getMapper(ModelEntityMapper.class);
     
     @Mapping( target = "brand" , source = "brandId")
     Model toModel(ModelDto dto);
     
     
     @Mapping(target = "brandId" , source = "brand.id")
     ModelDto toModelDto(Model model);
	    

	
}
