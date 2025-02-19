package com.example.transactions.service.contract;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.service.generic.ReadOnlyService;

public interface CategoryService extends ReadOnlyService<CategoryDTO, Long> {
    CategoryDTO create(CategoryDTO categoryDTO);
    void delete(Long id);
}
