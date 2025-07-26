package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.productEntityToProductDTO(product);
    }

    @Override
    public PagedResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return new PagedResponse<>(
            page.map(productMapper::productEntityToProductDTO).getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious()
        );
    }

    @Override
    public ProductDTO createProduct(CreateProductDTO createProductDTO) {
        Product product = productMapper.createProductDTOToProduct(createProductDTO);
        Product saved = productRepository.save(product);
        return productMapper.productEntityToProductDTO(saved);
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productMapper.updateProductFromDTO(updateProductDTO, product); // Método @MappingTarget
        Product updated = productRepository.save(product);
        return productMapper.productEntityToProductDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
