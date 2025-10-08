package com.ecommerce.project.service;

import com.ecommerce.project.payload.dtos.CategoryDTO;
import com.ecommerce.project.payload.responses.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);
}
