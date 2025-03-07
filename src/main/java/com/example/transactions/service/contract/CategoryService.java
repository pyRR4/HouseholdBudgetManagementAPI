package com.example.transactions.service.contract;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.service.generic.CreateDeleteService;
import com.example.transactions.service.generic.ReadOnlyService;

public interface CategoryService extends
        ReadOnlyService<CategoryDTO, Long>,
        CreateDeleteService<CategoryDTO, Long> {
}
