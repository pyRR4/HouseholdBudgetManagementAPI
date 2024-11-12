package com.example.transactions.categories;

import com.example.transactions.exceptions.CategoryAlredyExistsException;
import com.example.transactions.exceptions.CategoryNotFoundException;
import com.example.transactions.users.UserEntity;
import com.example.transactions.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public List<CategoryEntity> getCategoriesByUsername(String username) {

        return categoryRepository.findAllByUserUsername(username)
                .stream().toList();
    }

    public CategoryEntity getCategoryByUsernameAndName(String username, String name) {

        return categoryRepository.findByUserUsernameAndName(username, name)
                .orElseThrow(() -> new CategoryNotFoundException(username));
    }

    public CategoryEntity createCategory(String categoryName, String username) throws DataIntegrityViolationException {
        if(categoryRepository.existsByUserUsernameAndName(categoryName, username))
            throw new CategoryAlredyExistsException(categoryName, username);

        log.info(username);
        UserEntity user = userService.getUserByUsername(username);
        log.info(user.toString());
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public void deleteCategory(String username, String categoryName) {
        if(!categoryRepository.existsByUserUsernameAndName(username, categoryName))
            throw new CategoryNotFoundException(categoryName);

        categoryRepository.deleteByUserUsernameAndName(username, categoryName);
    }
}
