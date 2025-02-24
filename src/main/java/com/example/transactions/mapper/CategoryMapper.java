package com.example.transactions.mapper;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<Category, CategoryDTO> {

    @Mapping(target = "user", source = "userDTO")
    Category toEntity(CategoryDTO dto);

    @Mapping(target = "userDTO", source = "user")
    CategoryDTO toDTO(Category entity);
}
