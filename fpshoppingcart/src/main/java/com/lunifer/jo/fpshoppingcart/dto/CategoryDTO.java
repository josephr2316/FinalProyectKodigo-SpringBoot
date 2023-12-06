package com.lunifer.jo.fpshoppingcart.dto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDTO {

    private long categoryId;
    private String categoryName;
    private boolean isActive;
    private List<ProductDTO> productList;
}
