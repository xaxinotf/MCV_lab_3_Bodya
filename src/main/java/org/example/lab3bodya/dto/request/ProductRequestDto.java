package org.example.lab3bodya.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRequestDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private Long countryId;
}
