package com.example.transactions.services;

import com.example.transactions.dto.CategoryDTO;
import com.example.transactions.entity.Category;
import com.example.transactions.entity.User;
import com.example.transactions.exception.CategoryNotFound;
import com.example.transactions.mapper.CategoryMapper;
import com.example.transactions.repository.CategoryRepository;
import com.example.transactions.service.contract.implementation.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setBalance(BigDecimal.ZERO);

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setTransactions(List.of());
        category.setUser(user);

        user.setCategories(List.of(category));

        categoryDTO = categoryMapper.toDTO(category);
    }

    @Test
    void shouldCreateCategory() {
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.create(categoryDTO);

        assertThat(result).isEqualTo(categoryDTO);
        verify(categoryRepository).save(category);
    }

    @Test
    void shouldDeleteCategoryIfExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.delete(1L);

        verify(categoryRepository).delete(category);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(CategoryNotFound.class)
                .hasMessage("Category with ID: 1 not found.");
    }

    @Test
    void shouldGetCategoryByIdIfExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getById(1L);

        assertThat(result).isEqualTo(categoryDTO);
    }

    @Test
    void shouldThrowWhenGettingNonExistingCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(1L))
                .isInstanceOf(CategoryNotFound.class)
                .hasMessage("Category with ID: 1 not found.");
    }

    @Test
    void shouldGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        List<CategoryDTO> result = categoryService.getAll();

        assertThat(result).containsExactly(categoryDTO);
    }
}

