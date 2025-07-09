package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean Active;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> products;

}
