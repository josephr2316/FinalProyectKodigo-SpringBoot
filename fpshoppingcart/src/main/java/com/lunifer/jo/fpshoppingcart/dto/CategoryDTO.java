package com.lunifer.jo.fpshoppingcart.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private long categoryId;
    private String categoryName;
    private boolean isActive;
    private List<ProductDTO> productList;
}
