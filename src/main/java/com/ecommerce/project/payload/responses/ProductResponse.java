package com.ecommerce.project.payload.responses;

import java.util.List;

import com.ecommerce.project.payload.dtos.PaginationDTO;
import com.ecommerce.project.payload.dtos.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private List<ProductDTO> content;
    private PaginationDTO pagination;
}
