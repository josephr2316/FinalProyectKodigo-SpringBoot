package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.*;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductDTO getProductById(Long id);
    PagedResponse<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO createProduct(CreateProductDTO createProductDTO);
    ProductDTO updateProduct(Long id, UpdateProductDTO updateProductDTO);
    void deleteProduct(Long id);
}