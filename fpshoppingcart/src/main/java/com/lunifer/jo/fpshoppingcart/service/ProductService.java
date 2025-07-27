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

public interface ProductService {
    ProductDTO getProductById(Long id);
    PagedResponse<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO createProduct(CreateProductDTO createProductDTO);
    ProductDTO updateProduct(Long id, UpdateProductDTO updateProductDTO);
    void deleteProduct(Long id);
}