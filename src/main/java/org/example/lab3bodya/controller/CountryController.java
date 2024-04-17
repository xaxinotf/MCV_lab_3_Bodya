package org.example.lab3bodya.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.lab3bodya.dto.request.CountryRequestDto;
import org.example.lab3bodya.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
@NoArgsConstructor
@AllArgsConstructor
public class CountryController {

    private CountryService countryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CountryRequestDto countryRequestDto) {
        countryService.handleCountryRequest(countryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(countryService.getAllCountries());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(countryService.getCountryById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
