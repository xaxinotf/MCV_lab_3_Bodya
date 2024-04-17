package org.example.lab3bodya;

import org.example.lab3bodya.dto.ProductDto;
import org.example.lab3bodya.dto.request.ProductRequestDto;
import org.example.lab3bodya.dto.response.AllProductResponseDto;
import org.example.lab3bodya.dto.response.ProductResponseDto;
import org.example.lab3bodya.mapper.ProductMapper;
import org.example.lab3bodya.model.Category;
import org.example.lab3bodya.model.Country;
import org.example.lab3bodya.model.Product;
import org.example.lab3bodya.repository.CategoryRepository;
import org.example.lab3bodya.repository.CountryRepository;
import org.example.lab3bodya.repository.ProductRepository;
import org.example.lab3bodya.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;
    @Spy
    private ProductService spyProductService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        spyProductService = Mockito.spy(productService);
    }

    @Test
    void saveProductWhenCategoryAndCountryExistTest() {
        ProductRequestDto requestDto = new ProductRequestDto(1L, "Laptop", new BigDecimal("1200.00"), 1L, 1L);
        Category category = new Category(1L, "Electronics", "Devices");
        Country country = new Country(1L, "USA");
        Product product = new Product(1L, "Laptop", new BigDecimal("1200.00"), category, country);

        when(categoryRepository.findById(requestDto.getCategoryId())).thenReturn(Optional.of(category));
        when(countryRepository.findById(requestDto.getCountryId())).thenReturn(Optional.of(country));
        when(productMapper.productRequestDtoToProduct(requestDto)).thenReturn(product);

        productService.saveProduct(requestDto);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductByIdFoundTest() {
        Long id = 1L;
        Product product = new Product(id, "Laptop", new BigDecimal("1200.00"), new Category(), new Country());
        ProductDto productDto = new ProductDto(id.toString(), "Laptop", new BigDecimal("1200.00"), "Electronics", "USA");

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        ProductResponseDto result = productService.getById(id);

        assertEquals(new ProductResponseDto(productDto), result);
    }

    @Test
    void getProductByIdNotFoundTest() {
        Long id = 999L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getById(id);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void getAllProductsTest() {
        List<Product> products = List.of(new Product(1L, "Laptop", new BigDecimal("1200.00"), new Category(), new Country()));
        List<ProductDto> productDtos = List.of(new ProductDto("1", "Laptop", new BigDecimal("1200.00"), "Electronics", "USA"));

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productToProductDto(any(Product.class))).thenAnswer(i -> new ProductDto(Long.toString(((Product)i.getArgument(0)).getId()), ((Product)i.getArgument(0)).getName(), ((Product)i.getArgument(0)).getPrice(), "Electronics", "USA"));

        AllProductResponseDto result = productService.getAll();

        assertEquals(new AllProductResponseDto(productDtos), result);
    }

    @Test
    void deleteProductByIdTest() {
        Long id = 1L;

        doNothing().when(productRepository).deleteById(id);

        productService.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
    }
    @Test
    void getAllProductsUsingSpyTest() {
        List<Product> products = List.of(
                new Product(1L, "Laptop", new BigDecimal("1200.00"), new Category(), new Country()),
                new Product(2L, "Monitor", new BigDecimal("300.00"), new Category(), new Country())
        );
        List<ProductDto> productDtos = List.of(
                new ProductDto("1", "Laptop", new BigDecimal("1200.00"), "Electronics", "USA"),
                new ProductDto("2", "Monitor", new BigDecimal("300.00"), "Electronics", "USA")
        );

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.productToProductDto(any(Product.class))).thenAnswer(i -> {
            Product p = i.getArgument(0);
            return new ProductDto(Long.toString(p.getId()), p.getName(), p.getPrice(), "Electronics", "USA");
        });

        AllProductResponseDto result = spyProductService.getAll();

        assertEquals(new AllProductResponseDto(productDtos), result);
        verify(spyProductService, times(1)).getAll();  // Verify real method call
        verify(productRepository, times(1)).findAll();  // Ensure findAll is called
        verify(productMapper, times(products.size())).productToProductDto(any(Product.class));  // Ensure each product is mapped
    }
}
