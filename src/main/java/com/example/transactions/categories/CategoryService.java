package com.example.transactions.categories;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/category")
    public CategoryEntity createCategory(CategoryEntity category) {

        return categoryRepository.save(category);
    }

    @DeleteMapping("/category")
    public void deleteCategory(CategoryEntity category) {

        categoryRepository.delete(category);
    }
}
