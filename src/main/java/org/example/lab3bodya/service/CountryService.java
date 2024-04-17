package org.example.lab3bodya.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.lab3bodya.dto.request.CountryRequestDto;
import org.example.lab3bodya.dto.response.AllCountryResponseDto;
import org.example.lab3bodya.dto.response.CountryResponseDto;
import org.example.lab3bodya.mapper.CountryMapper;
import org.example.lab3bodya.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CountryService {

    private CountryRepository countryRepository;
    private CountryMapper countryMapper;

    @Transactional
    public void handleCountryRequest(CountryRequestDto countryRequestDto) {
        var country = countryMapper.countryRequestDtoToCountry(countryRequestDto);

        countryRepository.save(country);
    }

    public CountryResponseDto getCountryById(Long id) {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country with id: " +
                        id + " not found"));
        var countryDto = countryMapper.countryToCountryDto(country);

        return new CountryResponseDto(countryDto);
    }

    public AllCountryResponseDto getAllCountries() {
        var countries = countryRepository.findAll()
                .stream()
                .map(countryMapper::countryToCountryDto)
                .toList();

        return new AllCountryResponseDto(countries);
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
