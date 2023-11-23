package com.lunifer.jo.fpshoppingcart.dto;
import jdk.jfr.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean isActive;
    private Category category;
}
