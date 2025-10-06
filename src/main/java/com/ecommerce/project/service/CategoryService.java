package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories();

    void createCategory(Category category);

    void deleteCategory(Long categoryId);

    void updateCategory(Long categoryId, Category category);
}
