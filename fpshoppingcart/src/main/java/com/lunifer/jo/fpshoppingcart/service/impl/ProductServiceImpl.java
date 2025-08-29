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
        return productRepository.findById(id)
                .map(productMapper::toProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public PagedResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return PagedResponse.of(products.map(productMapper::toProductDTO));
    }

    @Override
    public ProductDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.toProduct(dto);
        return productMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productMapper.updateProductFromDTO(dto, product);
        return productMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }
}
