package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CreateProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.UpdateProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.lunifer.jo.fpshoppingcart.service.ProductService;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productEntityToProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public PagedResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return PagedResponse.of(products.map(productMapper::productEntityToProductDTO));
    }

    @Override
    public ProductDTO createProduct(CreateProductDTO createProductDTO) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductDTO updateProductDTO) {
        return null;
    }

    @Override
    public ProductDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.createProductDTOToProduct(dto);
        return productMapper.productEntityToProductDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productMapper.updateProductFromDTO(dto, product);
        return productMapper.productEntityToProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
