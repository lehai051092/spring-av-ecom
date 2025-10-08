package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.dtos.CategoryDTO;
import com.ecommerce.project.payload.dtos.PaginationDTO;
import com.ecommerce.project.payload.responses.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return getCategoryResponse(categoryDTOS, categoryPage);
    }

    private static CategoryResponse getCategoryResponse(List<CategoryDTO> categoryDTOS, Page<Category> categoryPage) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPageNumber(categoryPage.getNumber());
        paginationDTO.setPageSize(categoryPage.getSize());
        paginationDTO.setTotalElements(categoryPage.getTotalElements());
        paginationDTO.setTotalPages(categoryPage.getTotalPages());
        paginationDTO.setLastPage(categoryPage.isLast());

        categoryResponse.setPagination(paginationDTO);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (savedCategory != null) {
            throw new APIException("Category with name " + categoryDTO.getCategoryName() + " already exists");
        }

        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "categoryId", categoryId)
                );

        categoryRepository.deleteById(category.getCategoryId());
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "categoryId", categoryId)
                );
        existedCategory.setCategoryName(categoryDTO.getCategoryName());

        Category updatedCategory = categoryRepository.save(existedCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
