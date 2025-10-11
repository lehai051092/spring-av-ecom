package com.ecommerce.project.service;

import com.ecommerce.project.payload.dtos.ProductDTO;
import com.ecommerce.project.payload.responses.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts();

    ProductResponse getAllProductsByCategory(Long catetgoryId);

    ProductResponse searchProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
}
