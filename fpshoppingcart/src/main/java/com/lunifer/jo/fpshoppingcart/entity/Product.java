package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private Long productId;

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

    @ManyToMany(mappedBy ="productList", fetch = FetchType.LAZY)
    private Set<ShoppingCart> shoppingCart;

    @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY)
    private Set<Order> order;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Category category;
}
