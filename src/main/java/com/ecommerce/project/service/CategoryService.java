package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    void deleteCategory(Long categoryId);

    void updateCategory(Long categoryId, Category category);
}
