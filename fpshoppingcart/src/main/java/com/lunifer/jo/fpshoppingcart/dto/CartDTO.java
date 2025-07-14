package com.lunifer.jo.fpshoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Long cartId;
    private Long userId;
    private String username;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
    private int totalItems;
    private boolean empty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
