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
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productImageUrl; // Opcional
    private int quantity;
    private BigDecimal subtotal;
    private boolean validQuantity;
    private int availableStock;
}