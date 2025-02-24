package com.example.transactions.mappers;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.Category;
import com.example.transactions.entity.User;
import com.example.transactions.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "john_doe", "securePass123", "john@example.com", null, null, null);
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), 1000.0);

        category = new Category(1L, "Food", user, null);
        categoryDTO = new CategoryDTO(category.getId(), category.getName(), userDTO);
    }

    @Test
    void shouldMapCategoryToDTO() {
        CategoryDTO dto = categoryMapper.toDTO(category);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(category.getId());
        assertThat(dto.name()).isEqualTo(category.getName());
        assertThat(dto.userDTO().id()).isEqualTo(category.getUser().getId());
    }

    @Test
    void shouldMapCategoryDTOToEntity() {
        Category entity = categoryMapper.toEntity(categoryDTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(categoryDTO.id());
        assertThat(entity.getName()).isEqualTo(categoryDTO.name());
        assertThat(entity.getUser().getId()).isEqualTo(categoryDTO.userDTO().id());
    }
}
