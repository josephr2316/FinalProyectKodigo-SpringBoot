package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToOne
    @JoinColumn (nullable = false)
    private ShoppingCart shoppingCart;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Category category;
}
