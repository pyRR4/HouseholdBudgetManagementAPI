package com.example.transactions.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
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
