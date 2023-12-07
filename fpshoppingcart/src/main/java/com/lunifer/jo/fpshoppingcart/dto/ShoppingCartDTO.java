package com.lunifer.jo.fpshoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private Set<ProductDTO> productList;
    private BigDecimal totalPrice;

}