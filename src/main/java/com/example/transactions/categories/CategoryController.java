package com.example.transactions.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/users/{username}/categories")
    public ResponseEntity<List<CategoryEntity>> getUserCategories(@PathVariable String username) {
        List<CategoryEntity> categoryEntity = categoryService.getCategoriesByUsername(username);

        return ResponseEntity
                .ok(categoryEntity);
    }

    @PostMapping("/users/{username}/categories")
    public ResponseEntity<CategoryEntity> addCategory(@PathVariable String username, @RequestParam String category) {
        CategoryEntity categoryEntity = categoryService.createCategory(category, username);

        return ResponseEntity
                .created(linkTo(methodOn(CategoryController.class)
                        .getUserCategories(categoryEntity.getUser().getUsername())).toUri())
                .body(categoryEntity);
    }

    @DeleteMapping("/users/{username}/{category}")
    public ResponseEntity<CategoryEntity> deleteCategory(@PathVariable String username, @PathVariable String category) {
        categoryService.deleteCategory(username, category);

        return ResponseEntity.noContent().build();
    }
}
