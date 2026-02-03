package com.sa.leanning.CarProject.Mapper;

import org.mapstruct.Mapper;

import com.sa.leanning.CarProject.DTO.ColorDto;
import com.sa.leanning.CarProject.Entities.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDto toColorDto(Color color);

    Color toColor(ColorDto colorDto);
}
