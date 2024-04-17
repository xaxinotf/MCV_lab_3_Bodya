package org.example.lab3bodya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lab3bodya.dto.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
public class AllProductResponseDto {

    private List<ProductDto> products;
}
