package com.lunifer.jo.fpshoppingcart.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean isActive;
    private long categoryId;
}
