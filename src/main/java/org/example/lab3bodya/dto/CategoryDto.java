package org.example.lab3bodya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
}
