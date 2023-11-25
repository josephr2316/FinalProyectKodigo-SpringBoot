package com.lunifer.jo.fpshoppingcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String userName;

    private String password;

    //role:Enum
    private Boolean isActive;

    private List<Order> orderHistory;

    private List<Review> reviewHistory;
}
