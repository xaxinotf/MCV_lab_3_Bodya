package org.example.lab3bodya.mapper;

import org.example.lab3bodya.dto.ProductDto;
import org.example.lab3bodya.dto.request.ProductRequestDto;
import org.example.lab3bodya.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product productRequestDtoToProduct(ProductRequestDto productRequestDto);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "category", source = "category.name")
    @Mapping(target = "country", source = "country.name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "name", source = "name")
    ProductDto productToProductDto(Product product);
}
