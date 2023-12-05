package com.lunifer.jo.fpshoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private List<ProductDTO> productList;
    private BigDecimal totalPrice;

}