package com.example.transactions.categories;

import com.example.transactions.exceptions.CategoryAlredyExistsException;
import com.example.transactions.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity getCategoryByName(String name) {

        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public CategoryEntity createCategory(CategoryEntity category) throws DataIntegrityViolationException {
        if(categoryRepository.existsByName(category.getName()))
            throw new CategoryAlredyExistsException(category.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryName) {
        if(!categoryRepository.existsByName(categoryName))
            throw new CategoryNotFoundException(categoryName);

        categoryRepository.deleteByName(categoryName);
    }
}
