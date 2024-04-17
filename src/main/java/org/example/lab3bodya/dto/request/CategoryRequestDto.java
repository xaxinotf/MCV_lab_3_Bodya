package org.example.lab3bodya.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequestDto {

    private Long id;
    private String name;
    private String description;
}
