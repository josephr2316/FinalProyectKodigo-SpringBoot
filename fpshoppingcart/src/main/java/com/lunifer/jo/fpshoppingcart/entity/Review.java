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
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean likeDislike;

    @ManyToOne
    @JoinColumn(nullable = false)
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
