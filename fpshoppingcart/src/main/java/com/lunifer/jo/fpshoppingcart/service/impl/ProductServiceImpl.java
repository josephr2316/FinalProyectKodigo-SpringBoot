package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              CategoryMapper categoryMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapProductToDTOWithCategory)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO getProductById(long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productEntityToProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, long productId) {
        // 1. Check whether the product with the given ID exists in DB or not
        //Throw exception
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // 2. Map the updated fields from productDTO to the existing productEntity
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setActive(productDTO.isActive());
        existingProduct.setCategory(categoryMapper.categoryDTOToCategoryEntity(categoryService.getCategoryById(productDTO.getCategoryId())));

        // 3. Save the updated productEntity back to the database
        Product updatedProductEntity = productRepository.save(existingProduct);

        // 4. Map the updated productEntity to a ProductDTO and return it
        return productMapper.productEntityToProductDTO(updatedProductEntity);

    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // Perform validation or transformation logic before saving to the database
        validateProductDTO(productDTO); // Your custom validation method

        // Map the DTO to an entity and save it to the database
        Product productEntityToSave = productMapper.productDTOToProductEntity(productDTO);
        Product savedProductEntity = productRepository.save(productEntityToSave);

        // Map the saved entity back to a DTO and return it
        return productMapper.productEntityToProductDTO(savedProductEntity);

    }

    @Override
    public void deleteProduct(long productId) {
        // Throw exception
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productRepository.deleteById(productId);
    }

    private void validateProductDTO(ProductDTO productDTO) {

        // Check if product name is not null and not empty
        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        // Check if price is not null and greater than zero
        if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        // Check if stock is not negative
        if (productDTO.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        // Check if the category is provided (assuming you have a CategoryDTO in ProductDTO)
        if (productDTO.getCategoryId() != 0) {
            throw new IllegalArgumentException("Product category is required");
        }
    }

    @Override
    public boolean disableProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(!product.isActive());
            return true;
        }

        return false;
    }

    private ProductDTO mapProductToDTOWithCategory(Product product) {
        ProductDTO productDTO = productMapper.productEntityToProductDTO(product);
        CategoryDTO categoryDTO = categoryMapper.categoryEntityToCategoryDTO(product.getCategory());
        productDTO.setCategoryId(categoryDTO.getCategoryId());
        return productDTO;
    }
}
