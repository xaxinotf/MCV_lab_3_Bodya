package org.example.lab3bodya.mapper;

import org.example.lab3bodya.dto.CountryDto;
import org.example.lab3bodya.dto.request.CountryRequestDto;
import org.example.lab3bodya.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {

    Country countryRequestDtoToCountry(CountryRequestDto countryRequestDto);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDto countryToCountryDto(Country country);
}
