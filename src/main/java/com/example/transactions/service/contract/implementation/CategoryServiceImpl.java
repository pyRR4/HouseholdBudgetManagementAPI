package com.example.transactions.service.contract.implementation;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.entity.Category;
import com.example.transactions.exception.CategoryNotFound;
import com.example.transactions.mapper.CategoryMapper;
import com.example.transactions.repository.CategoryRepository;
import com.example.transactions.service.contract.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);

        categoryRepository.save(category);

        log.info("Created category: {}", category);

        return categoryMapper.toDTO(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));

        categoryRepository.delete(category);

        log.info("Deleted category: {}", category);
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));

        log.info("Retrieved category: {}", category);

        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();

        log.info("Retrieved categories: {}", categories.size());

        return categories.stream()
                .map(categoryMapper::toDTO)
                .toList();
    }
}
