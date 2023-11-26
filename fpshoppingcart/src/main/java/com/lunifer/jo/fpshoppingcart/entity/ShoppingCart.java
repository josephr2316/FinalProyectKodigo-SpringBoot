package com.lunifer.jo.fpshoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long cartId;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns =@JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    @Column(nullable = false)
    private BigDecimal totalPrice;

}
