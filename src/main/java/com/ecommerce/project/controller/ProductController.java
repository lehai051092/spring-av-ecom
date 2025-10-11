package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.payload.dtos.ProductDTO;
import com.ecommerce.project.payload.responses.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @PathVariable Long categoryId,
            @RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.addProduct(categoryId, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.getAllProductsByCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> searchProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductsByKeyword(keyword);

        return ResponseEntity.status(HttpStatus.FOUND).body(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        ProductDTO updateProductDTO = productService.updateProduct(productId, productDTO);
        
        return ResponseEntity.status(HttpStatus.OK).body(updateProductDTO);
    }
}
