package com.ecommerce.project.payload.responses;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.dtos.CategoryDTO;
import com.ecommerce.project.payload.dtos.PaginationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends AbstractResponse<CategoryResponse, Category, CategoryDTO> {

    private List<CategoryDTO> content;
    private PaginationDTO pagination;

    @Override
    public CategoryResponse generate(Page<Category> page, List<CategoryDTO> dtos) {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPageNumber(page.getNumber());
        paginationDTO.setPageSize(page.getSize());
        paginationDTO.setTotalElements(page.getTotalElements());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setLastPage(page.isLast());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(dtos);
        categoryResponse.setPagination(paginationDTO);
        return categoryResponse;
    }
}
