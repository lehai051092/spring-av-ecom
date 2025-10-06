package com.ecommerce.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId)
                );
        categoryRepository.deleteById(category.getCategoryId());
    }

    @Override
    public void updateCategory(Long categoryId, Category category) {
        Category existedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "id", categoryId)
                );
        existedCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existedCategory);
    }
}
