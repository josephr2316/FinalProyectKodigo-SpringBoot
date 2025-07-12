package com.lunifer.jo.fpshoppingcart.entity;

import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_active", columnList = "active")
})
@BatchSize(size = 20)
public class User extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

   // @Column(nullable = false)
    @ElementCollection(targetClass = UserRol.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRol> roles = new HashSet<>();

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @BatchSize(size = 15)
    private List<Order> orderHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    @BatchSize(size = 10)
    private List<Review> reviewHistory = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;


    // Business logic methods
    public void addRole(UserRol role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void removeRole(UserRol role) {
        if (roles != null) {
            roles.remove(role);
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean hasRole(UserRol role) {
        return roles != null && roles.contains(role);
    }

}