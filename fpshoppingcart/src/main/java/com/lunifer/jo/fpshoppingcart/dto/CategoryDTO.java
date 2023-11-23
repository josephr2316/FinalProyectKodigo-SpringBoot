package com.lunifer.jo.fpshoppingcart.dto;

import com.lunifer.jo.fpshoppingcart.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private long categoryId;
    private String categoryName;
    private boolean isActive;
    private List<Product> productList;
}
