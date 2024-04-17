package org.example.lab3bodya;


import org.example.lab3bodya.dto.CountryDto;
import org.example.lab3bodya.dto.request.CountryRequestDto;
import org.example.lab3bodya.dto.response.AllCountryResponseDto;
import org.example.lab3bodya.dto.response.CountryResponseDto;
import org.example.lab3bodya.mapper.CountryMapper;
import org.example.lab3bodya.model.Country;
import org.example.lab3bodya.repository.CountryRepository;
import org.example.lab3bodya.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    @Spy
    private CountryService spyCountryService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        spyCountryService = Mockito.spy(new CountryService(countryRepository, countryMapper));
    }

    @Test
    void handleCountryRequestTest() {
        CountryRequestDto requestDto = new CountryRequestDto(1L, "Ukraine");
        Country country = new Country(1L, "Ukraine");

        when(countryMapper.countryRequestDtoToCountry(requestDto)).thenReturn(country);

        countryService.handleCountryRequest(requestDto);

        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void getCountryByIdFoundTest() {
        Long id = 1L;
        Country country = new Country(id, "Ukraine");
        CountryDto countryDto = new CountryDto(id, "Ukraine");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(countryMapper.countryToCountryDto(country)).thenReturn(countryDto);

        CountryResponseDto result = countryService.getCountryById(id);

        assertEquals(new CountryResponseDto(countryDto), result);
    }

    @Test
    void getCountryByIdNotFoundTest() {
        Long id = 999L;

        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.getCountryById(id);
        });

        assertEquals("Country with id: 999 not found", exception.getMessage());
    }

    @Test
    void getAllCountriesTest() {
        List<Country> countries = List.of(new Country(1L, "Ukraine"), new Country(2L, "Poland"));
        List<CountryDto> countryDtos = List.of(new CountryDto(1L, "Ukraine"), new CountryDto(2L, "Poland"));

        when(countryRepository.findAll()).thenReturn(countries);
        when(countryMapper.countryToCountryDto(any(Country.class))).thenAnswer(i -> new CountryDto(((Country)i.getArgument(0)).getId(), ((Country)i.getArgument(0)).getName()));

        AllCountryResponseDto result = countryService.getAllCountries();

        assertEquals(new AllCountryResponseDto(countryDtos), result);
    }

    @Test
    void deleteCountryTest() {
        Long id = 1L;

        doNothing().when(countryRepository).deleteById(id);

        countryService.deleteCountry(id);

        verify(countryRepository, times(1)).deleteById(id);
    }

    @Test
    void getAllCountriesWithSpyTest() {
        List<Country> mockCountries = List.of(new Country(1L, "Ukraine"), new Country(2L, "Poland"));
        List<CountryDto> countryDtos = List.of(new CountryDto(1L, "Ukraine"), new CountryDto(2L, "Poland"));

        // Мокуємо залежний метод, який потрібно перевизначити
        when(countryRepository.findAll()).thenReturn(mockCountries);
        when(countryMapper.countryToCountryDto(any(Country.class))).thenAnswer(i -> new CountryDto(((Country)i.getArgument(0)).getId(), ((Country)i.getArgument(0)).getName()));

        AllCountryResponseDto result = spyCountryService.getAllCountries();

        assertEquals(new AllCountryResponseDto(countryDtos), result);
        verify(countryRepository, times(1)).findAll();
        verify(spyCountryService, times(1)).getAllCountries();
    }
}
