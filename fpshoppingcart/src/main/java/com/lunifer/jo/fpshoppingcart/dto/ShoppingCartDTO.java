package com.lunifer.jo.fpshoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private List<ProductDTO> productList;
    private BigDecimal totalPrice;

}