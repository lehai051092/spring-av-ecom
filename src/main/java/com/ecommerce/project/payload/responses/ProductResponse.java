package com.ecommerce.project.payload.responses;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.dtos.PaginationDTO;
import com.ecommerce.project.payload.dtos.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends AbstractResponse<ProductResponse, Product, ProductDTO> {

    private List<ProductDTO> content;
    private PaginationDTO pagination;

    @Override
    public ProductResponse generate(Page<Product> page, List<ProductDTO> dtos) {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPageNumber(page.getNumber());
        paginationDTO.setPageSize(page.getSize());
        paginationDTO.setTotalElements(page.getTotalElements());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setLastPage(page.isLast());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(dtos);
        productResponse.setPagination(paginationDTO);
        return productResponse;
    }
}
