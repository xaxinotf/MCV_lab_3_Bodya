package org.example.lab3bodya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lab3bodya.dto.CategoryDto;

@Data
@AllArgsConstructor
public class CategoryResponseDto {

    private CategoryDto category;
}
