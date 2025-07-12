package com.lunifer.jo.fpshoppingcart.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean active;
    private Long categoryId;
    private String categoryName;
    private Double averageRating;   // optional, for product reviews
    private Long reviewCount;       // optional, for product reviews
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean available;
    private boolean inStock;
}
