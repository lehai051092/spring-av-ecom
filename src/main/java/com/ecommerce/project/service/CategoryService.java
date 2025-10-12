package com.ecommerce.project.service;

import com.ecommerce.project.payload.dtos.CategoryDTO;
import com.ecommerce.project.payload.dtos.PageableDTO;
import com.ecommerce.project.payload.responses.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(PageableDTO pageableDTO);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO category);
}
