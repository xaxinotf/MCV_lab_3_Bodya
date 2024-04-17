package org.example.lab3bodya.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.lab3bodya.dto.request.ProductRequestDto;
import org.example.lab3bodya.dto.response.AllProductResponseDto;
import org.example.lab3bodya.dto.response.ProductResponseDto;
import org.example.lab3bodya.mapper.ProductMapper;
import org.example.lab3bodya.repository.CategoryRepository;
import org.example.lab3bodya.repository.CountryRepository;
import org.example.lab3bodya.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProductService {

    private CategoryRepository categoryRepository;
    private CountryRepository countryRepository;
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Transactional
    public void saveProduct(ProductRequestDto productRequestDto) {
        var country = countryRepository.findById(productRequestDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        var category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        var product = productMapper.productRequestDtoToProduct(productRequestDto);
        product.setCategory(category);
        product.setCountry(country);

        productRepository.save(product);
    }

    public ProductResponseDto getById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        var productDto = productMapper.productToProductDto(product);

        return new ProductResponseDto(productDto);
    }
    public AllProductResponseDto getAll() {
         var productDtoList = productRepository.findAll()
                 .stream()
                 .map(productMapper::productToProductDto)
                 .toList();

         return new AllProductResponseDto(productDtoList);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
