package com.example.transactions.controller;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.service.contract.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.getAll();

        log.info("Returning all categories for logged user: {}", categories.toString()); ///AUTHENTICATION SERVICE FOR RETRIEVING CURRENTLY LOGGED USER

        return ResponseEntity
                .ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getById(id);

        log.info("Returning category: {}", category.toString());

        return ResponseEntity
                .ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.create(categoryDTO);

        log.info("Created category: {}", createdCategory.toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);

        log.info("Deleted category: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
