package org.example.lab3bodya.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.lab3bodya.dto.request.CategoryRequestDto;
import org.example.lab3bodya.dto.response.AllCategoryResponseDto;
import org.example.lab3bodya.dto.response.CategoryResponseDto;
import org.example.lab3bodya.mapper.CategoryMapper;
import org.example.lab3bodya.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Transactional
    public void handleCategoryRequest(CategoryRequestDto categoryRequestDto) {
        var category = categoryMapper.categoryRequestDtoToCategory(categoryRequestDto);

        categoryRepository.save(category);
    }

    public CategoryResponseDto getCategoryById(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " +
                        id + " not found"));
        var countryDto = categoryMapper.categoryToCategoryDto(category);

        return new CategoryResponseDto(countryDto);
    }

    public AllCategoryResponseDto getAllCategory() {
        var categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .toList();

        return new AllCategoryResponseDto(categories);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
