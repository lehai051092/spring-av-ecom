package com.ecommerce.project.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO saveCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(saveCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category with ID " + categoryId + " deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category category) {
        categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>("Category with ID " + categoryId + " updated successfully", HttpStatus.OK);
    }
}
