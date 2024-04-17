package org.example.lab3bodya.mapper;

import org.example.lab3bodya.dto.CategoryDto;
import org.example.lab3bodya.dto.request.CategoryRequestDto;
import org.example.lab3bodya.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {


    Category categoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    CategoryDto categoryToCategoryDto(Category category);
}
