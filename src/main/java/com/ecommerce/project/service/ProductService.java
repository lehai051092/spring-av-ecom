package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.dtos.PageableDTO;
import com.ecommerce.project.payload.dtos.ProductDTO;
import com.ecommerce.project.payload.responses.ProductResponse;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts(PageableDTO pageableDTO);

    ProductResponse getAllProductsByCategory(Long categoryId, PageableDTO pageableDTO);

    ProductResponse searchProductsByKeyword(String keyword, PageableDTO pageableDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO uploadProductImage(Long productId, MultipartFile image) throws IOException;
}
