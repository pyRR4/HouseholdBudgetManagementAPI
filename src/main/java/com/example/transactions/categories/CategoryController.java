package com.example.transactions.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{category}")
    public ResponseEntity<CategoryEntity> getCategory(@PathVariable String category) {
        CategoryEntity categoryEntity = categoryService.getCategoryByName(category);

        return ResponseEntity
                .ok(categoryEntity);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity category) {
        CategoryEntity categoryEntity = categoryService.createCategory(category);

        return ResponseEntity
                .created(linkTo(methodOn(CategoryController.class)
                        .getCategory(categoryEntity.getName())).toUri())
                .body(categoryEntity);
    }

    @DeleteMapping("/{category}")
    public ResponseEntity<CategoryEntity> deleteCategory(@PathVariable String category) {
        categoryService.deleteCategory(category);

        return ResponseEntity.noContent().build();
    }
}
