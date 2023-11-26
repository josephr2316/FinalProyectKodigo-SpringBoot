package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Product product;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private boolean likeDislike;

    @ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
