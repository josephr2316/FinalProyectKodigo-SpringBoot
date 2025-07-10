package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "product_name"),
        @Index(name = "idx_product_category", columnList = "category_id"),
        @Index(name = "idx_product_active", columnList = "active"),
        @Index(name = "idx_product_price", columnList = "price")
})
@BatchSize(size = 25) // Improves performance when fetching collections in batches.
public class Product extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

   // @ManyToMany(mappedBy ="productList", fetch = FetchType.LAZY)
    //private Set<ShoppingCart> shoppingCart;

   // @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY)
    //private Set<Order> order;*/

    // Many products can belong to one category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // One product can have many reviews
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // OneToMany the is explicit the lazy fetch
    @BatchSize(size = 15)
    private List<Review> reviews = new ArrayList<>();

    // Formula fields for average rating and review count (calculated on the fly by the DB)
    @Formula("(SELECT AVG(CASE WHEN r.like_dislike = true THEN r.rating ELSE 0 END) FROM reviews r WHERE r.product_id = product_id)")
    private Double averageRating;

    @Formula("(SELECT COUNT(r.review_id) FROM reviews r WHERE r.product_id = product_id)")
    private Long reviewCount;

    // Convenience methods

    /**
     * Checks if the product is in stock.
     */
    public boolean isInStock() {
        return stock > 0;
    }

    /**
     * Checks if there is enough stock for the given quantity.
     */
    public boolean hasStock(int quantity) {
        return stock >= quantity;
    }

    /**
     * Reduces the stock by the given quantity.
     */
    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        this.stock -= quantity;
    }

    /**
     * Adds the specified quantity to the stock.
     */
    public void addStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.stock += quantity;
    }

    /**
     * Checks if the product is available (active and in stock).
     */
    public boolean isAvailable() {
        return active && isInStock();
    }

    public Double getAverageRating() {
        return averageRating != null ? averageRating : 0.0;
    }

    public Long getReviewCount() {
        return reviewCount != null ? reviewCount : 0L;
    }
}
