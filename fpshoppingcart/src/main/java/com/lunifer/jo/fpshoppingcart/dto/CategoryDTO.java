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
    private Long productCount;        // Optional: cantidad total de productos en esta categoría
    private Long activeProductCount;  // Optional: cantidad de productos activos
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
