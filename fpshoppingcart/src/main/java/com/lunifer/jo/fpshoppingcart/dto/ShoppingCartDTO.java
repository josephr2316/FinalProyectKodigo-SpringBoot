package com.lunifer.jo.fpshoppingcart.dto;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private List<ProductDTO> productList;
    private BigDecimal totalPrice;

}