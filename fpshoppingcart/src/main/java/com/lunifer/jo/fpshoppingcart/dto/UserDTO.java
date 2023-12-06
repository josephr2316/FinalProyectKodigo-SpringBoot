package com.lunifer.jo.fpshoppingcart.dto;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.entity.UserRol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String userName;

    private String password;

    List<String> roles;

    private boolean isActive;

    private List<Order> orderHistory;

    private List<Review> reviewHistory;
}
