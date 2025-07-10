package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", indexes = {
        @Index(name = "idx_category_name", columnList = "category_name"),
        @Index(name = "idx_category_active", columnList = "active")
})
@BatchSize(size = 10)
public class Category extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String categoryName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

    // One category can have many products
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private Set<Product> products = new HashSet<>();

    // Formula fields for product counts (calculated on the fly by the DB)
    @Formula("(SELECT COUNT(p.product_id) FROM products p WHERE p.category_id = category_id)")
    private Long productCount;

    @Formula("(SELECT COUNT(p.product_id) FROM products p WHERE p.category_id = category_id AND p.active = true)")
    private Long activeProductCount;

    // Convenience methods

    public Long getProductCount() {
        return productCount != null ? productCount : 0L;
    }

    public Long getActiveProductCount() {
        return activeProductCount != null ? activeProductCount : 0L;
    }

    public void addProduct(Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        if (products != null) {
            products.remove(product);
            product.setCategory(null);
        }
    }
}
