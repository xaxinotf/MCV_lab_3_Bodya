package org.example.lab3bodya.repository;

import org.example.lab3bodya.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
