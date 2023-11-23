package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(productMapper::productEntityToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(long productId) {
        return productRepository.findById(productId)
                .map(productMapper::productEntityToProductDTO)
                .orElse(null);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, long productId) {
        // 1. Check whether the product with the given ID exists in DB or not
        //Throw exception
        Product existingProduct = productRepository.findById(productId).orElseThrow();

        // 2. Map the updated fields from productDTO to the existing productEntity
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setActive(productDTO.isActive());
        // Implement category
        //existingProduct.g

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
        /*return productMapper.productEntityToProductDTO(
                productRepository.save(productMapper.productDTOToProductEntity(productDTO)));*/
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

        // Example: Check if the category is provided (assuming you have a CategoryDTO in ProductDTO)
        if (productDTO.getCategory() == null) {
            throw new IllegalArgumentException("Product category is required");
        }

        // Example: Check if the category name is not null and not empty
       /* if (productDTO.getCategory().getCategoryName() == null || productDTO.getCategory().getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }*/
    }

    @Override
    public void deleteProduct(long productId) {
        // Throw exception
        productRepository.findById(productId).orElseThrow();
        productRepository.deleteById(productId);
    }
}
