package com.lunifer.jo.fpshoppingcart.entity;

import java.math.BigDecimal;

public class ProductDTO {

    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private Boolean isActive;

    // Constructor, getters y setters

    // Ejemplo de constructor:
    public ProductDTO(Long productId, String productName, String description, BigDecimal price, int stock, Boolean isActive) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
    }

    // Getters y setters para cada atributo
}
