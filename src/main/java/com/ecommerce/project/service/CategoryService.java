package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long categoryId);

    void updateCategory(Long categoryId, Category category);
}
