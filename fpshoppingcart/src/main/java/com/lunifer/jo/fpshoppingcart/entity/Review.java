package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_product", columnList = "product_id"),
        @Index(name = "idx_review_user", columnList = "user_id"),
        @Index(name = "idx_review_rating", columnList = "rating")
})
public class Review extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Column(name = "like_dislike", nullable = false, columnDefinition = "boolean default true")
    private boolean likeDislike = true;

    // ===== CONVENIENCE METHODS =====

    public boolean isPositive() {
        return rating >= 4;
    }

    public boolean isNegative() {
        return rating <= 2;
    }

    public boolean isNeutral() {
        return rating == 3;
    }

    //@ManyToOne
    //@JoinColumn(nullable = false)
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
    //private User user;
}
