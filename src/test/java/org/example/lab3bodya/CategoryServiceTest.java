package org.example.lab3bodya;

import org.example.lab3bodya.dto.CategoryDto;
import org.example.lab3bodya.dto.request.CategoryRequestDto;
import org.example.lab3bodya.dto.response.AllCategoryResponseDto;
import org.example.lab3bodya.dto.response.CategoryResponseDto;
import org.example.lab3bodya.mapper.CategoryMapper;
import org.example.lab3bodya.model.Category;
import org.example.lab3bodya.repository.CategoryRepository;
import org.example.lab3bodya.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Spy
    private CategoryService spyCategoryService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        spyCategoryService = Mockito.spy(categoryService);
    }

    @Test
    void handleCategoryRequestTest() {
        CategoryRequestDto requestDto = new CategoryRequestDto(1L, "Electronics", "Devices");
        Category category = new Category(1L, "Electronics", "Devices");

        when(categoryMapper.categoryRequestDtoToCategory(requestDto)).thenReturn(category);

        categoryService.handleCategoryRequest(requestDto);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void getCategoryByIdFoundTest() {
        Long id = 1L;
        Category category = new Category(id, "Electronics", "Devices");
        CategoryDto categoryDto = new CategoryDto(id, "Electronics", "Devices");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryResponseDto result = categoryService.getCategoryById(id);

        assertEquals(new CategoryResponseDto(categoryDto), result);
    }

    @Test
    void getCategoryByIdNotFoundTest() {
        Long id = 999L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.getCategoryById(id);
        });

        assertEquals("Category with id: 999 not found", exception.getMessage());
    }

    @Test
    void getAllCategoryTest() {
        List<Category> categories = List.of(new Category(1L, "Electronics", "Devices"), new Category(2L, "Books", "All books"));
        List<CategoryDto> categoryDtos = List.of(new CategoryDto(1L, "Electronics", "Devices"), new CategoryDto(2L, "Books", "All books"));

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.categoryToCategoryDto(any(Category.class))).thenAnswer(i -> new CategoryDto(((Category)i.getArgument(0)).getId(), ((Category)i.getArgument(0)).getName(), ((Category)i.getArgument(0)).getDescription()));

        AllCategoryResponseDto result = categoryService.getAllCategory();

        assertEquals(new AllCategoryResponseDto(categoryDtos), result);
    }
    @Test
    void deleteCategoryTest() {
        Long id = 1L;

        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteCategory(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }
    @Test
    void getAllCategoryWithSpyTest() {
        List<Category> categories = List.of(new Category(1L, "Electronics", "Devices"), new Category(2L, "Books", "All books"));
        List<CategoryDto> categoryDtos = List.of(new CategoryDto(1L, "Electronics", "Devices"), new CategoryDto(2L, "Books", "All books"));

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.categoryToCategoryDto(any(Category.class))).thenAnswer(i -> new CategoryDto(((Category)i.getArgument(0)).getId(), ((Category)i.getArgument(0)).getName(), ((Category)i.getArgument(0)).getDescription()));

        AllCategoryResponseDto result = spyCategoryService.getAllCategory();

        assertEquals(new AllCategoryResponseDto(categoryDtos), result);
        verify(spyCategoryService, times(1)).getAllCategory();  // Верифікуємо виклик реального методу
    }
}
