package com.lunifer.jo.fpshoppingcart.dto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private long categoryId;
    private String categoryName;
    private boolean isActive;
    private List<ProductDTO> productList;
}
