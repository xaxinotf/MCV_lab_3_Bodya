package org.example.lab3bodya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lab3bodya.dto.CountryDto;

import java.util.List;

@Data
@AllArgsConstructor
public class AllCountryResponseDto {

    private List<CountryDto> countries;
}
