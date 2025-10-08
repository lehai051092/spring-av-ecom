package com.ecommerce.project.service;

import com.ecommerce.project.payload.dtos.ProductDTO;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
}
