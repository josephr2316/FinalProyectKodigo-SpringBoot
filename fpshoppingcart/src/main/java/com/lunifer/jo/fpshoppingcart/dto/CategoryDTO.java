package com.lunifer.jo.fpshoppingcart.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    private String description;
    private boolean active;
    private Long productCount;        // Optional: total number of products in this category
    private Long activeProductCount;  // Optional: number of active products
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
