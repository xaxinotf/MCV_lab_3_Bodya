package org.example.lab3bodya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lab3bodya.dto.ProductDto;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private ProductDto product;
}
