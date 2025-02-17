package com.example.transactions.mapper;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<Category, CategoryDTO> {}
