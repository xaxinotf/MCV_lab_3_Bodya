package org.example.lab3bodya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {

    private String id;
    private String name;
    private BigDecimal price;
    private String category;
    private String country;
}
