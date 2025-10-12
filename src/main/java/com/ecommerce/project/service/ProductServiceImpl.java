package com.ecommerce.project.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.dtos.PageableDTO;
import com.ecommerce.project.payload.dtos.ProductDTO;
import com.ecommerce.project.payload.responses.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private PageableService pageableService;

    @Value("${app.upload.dir:uploads/images}")
    private String uploadDir;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean ifProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product p : products) {
            if (p.getProductName().equals(productDTO.getProductName())) {
                ifProductNotPresent = false;
                break;
            }
        }

        if (!ifProductNotPresent) {
            throw new APIException("Product with name " + productDTO.getProductName() + " already exists");
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);

        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(PageableDTO pageableDTO) {
        Pageable pageDetails = pageableService.getPageDetails(pageableDTO);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return new ProductResponse().generate(productPage, productDTOs);
    }

    @Override
    public ProductResponse getAllProductsByCategory(Long categoryId, PageableDTO pageableDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Pageable pageDetails = pageableService.getPageDetails(pageableDTO);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return new ProductResponse().generate(productPage, productDTOs);
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword, PageableDTO pageableDTO) {
        Pageable pageDetails = pageableService.getPageDetails(pageableDTO);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%", pageDetails);
        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return new ProductResponse().generate(productPage, productDTOs);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());

        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO uploadProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String fileName = fileService.uploadImage(uploadDir, image);

        product.setImage(fileName);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
