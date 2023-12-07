package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartDTO getShoppingCartById(Long cartId);

    ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO);

    ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDTO, Long cartId);

    void deleteShoppingCart(Long cartId);

    ShoppingCartResponse getAllShoppingCarts(int pageNo, int pageSize, String sortBy, String sortDir);
    void deleteShoppingCartsForUser(User user);
}
