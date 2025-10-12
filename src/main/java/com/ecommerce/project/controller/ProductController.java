package com.ecommerce.project.controller;

import java.io.IOException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.dtos.PageableDTO;
import com.ecommerce.project.payload.dtos.ProductDTO;
import com.ecommerce.project.payload.responses.ProductResponse;
import com.ecommerce.project.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @PathVariable Long categoryId,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.addProduct(categoryId, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {

        PageableDTO pageableDTO = new PageableDTO(pageNumber, pageSize, sortBy, sortOrder);
        ProductResponse productResponse = productService.getAllProducts(pageableDTO);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {

        PageableDTO pageableDTO = new PageableDTO(pageNumber, pageSize, sortBy, sortOrder);
        ProductResponse productResponse = productService.getAllProductsByCategory(categoryId, pageableDTO);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> searchProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {

        PageableDTO pageableDTO = new PageableDTO(pageNumber, pageSize, sortBy, sortOrder);
        ProductResponse productResponse = productService.searchProductsByKeyword(keyword, pageableDTO);

        return ResponseEntity.status(HttpStatus.FOUND).body(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updateProductDTO = productService.updateProduct(productId, productDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updateProductDTO);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deleteProductDTO = productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.OK).body(deleteProductDTO);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(
            @PathVariable Long productId,
            @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO productDTO = productService.uploadProductImage(productId, image);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
}
