package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import com.lunifer.jo.fpshoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Long cartId) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCartById(cartId);
        return ResponseEntity.ok(shoppingCartDTO);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        ShoppingCartDTO createdShoppingCartDTO = shoppingCartService.createShoppingCart(shoppingCartDTO);
        return ResponseEntity.ok(createdShoppingCartDTO);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@PathVariable Long cartId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
        ShoppingCartDTO updatedShoppingCartDTO = shoppingCartService.updateShoppingCart(shoppingCartDTO, cartId);
        return ResponseEntity.ok(updatedShoppingCartDTO);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long cartId) {
        shoppingCartService.deleteShoppingCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ShoppingCartResponse getAllShoppingCarts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return shoppingCartService.getAllShoppingCarts(pageNo, pageSize);
    }
}
