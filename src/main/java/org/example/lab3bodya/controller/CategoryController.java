package org.example.lab3bodya.controller;

import lombok.RequiredArgsConstructor;
import org.example.lab3bodya.dto.request.CategoryRequestDto;
import org.example.lab3bodya.dto.request.CountryRequestDto;
import org.example.lab3bodya.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.handleCategoryRequest(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getAllCategory());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
