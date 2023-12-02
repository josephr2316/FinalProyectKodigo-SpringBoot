package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import java.util.List;


public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(long productId);
    ProductDTO updateProduct(ProductDTO productDTO, long productId);
    void deleteProduct(long productId);
     boolean disableProduct(Long productId);

}
