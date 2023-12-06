package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.payload.ProductResponse;

import java.util.List;


public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);

    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductDTO getProductById(long productId);
    ProductDTO getProductByName(String productName);
 
    ProductDTO updateProduct(ProductDTO productDTO, long productId);

    void deleteProduct(long productId);

    List<ProductDTO> getProductsByKeyword(String keyword);

    boolean disableProduct(Long productId);
    
     String DisableEnableProduct(Long productId);
  
}
