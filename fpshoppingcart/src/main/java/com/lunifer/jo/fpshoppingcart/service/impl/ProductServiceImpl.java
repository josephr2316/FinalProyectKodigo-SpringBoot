package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;

import java.util.List;

public interface ProductServiceImpl {
    ProductDTO saveProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(long productId);
    ProductDTO updateProduct(ProductDTO productDTO, long productId);
    void deleteProduct(long productId);
}
