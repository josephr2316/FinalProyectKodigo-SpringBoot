package com.lunifer.jo.fpshoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts", indexes = {
        @Index(name = "idx_cart_user", columnList = "user_id")
})
public class Cart extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", updatable = false)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JoinTable(
    //        name = "cart_product",
    //        joinColumns =@JoinColumn(name = "cart_id"),
    //        inverseJoinColumns = @JoinColumn(name = "product_id")
    //)
    //private Set<Product> productList;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    // Convenience methods
    public void addItem(CartItem item) {
        if (items == null) items = new ArrayList<>();
        Optional<CartItem> existing = findItemByProduct(item.getProduct());
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            items.add(item);
            item.setCart(this);
        }
        calculateTotalPrice();
    }

    public void removeItem(CartItem item) {
        if (items != null) {
            items.remove(item);
            calculateTotalPrice();
        }
    }

    public Optional<CartItem> findItemByProduct(Product product) {
        return items == null ? Optional.empty() :
                items.stream().filter(i -> i.getProduct().equals(product)).findFirst();
    }

    public void calculateTotalPrice() {
        if (items == null || items.isEmpty()) {
            totalPrice = BigDecimal.ZERO;
        } else {
            totalPrice = items.stream()
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public void clearCart() {
        if (items != null) items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public int getTotalItems() {
        return items == null ? 0 : items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

}
