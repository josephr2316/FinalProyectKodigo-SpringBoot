package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import com.lunifer.jo.fpshoppingcart.mapper.ShoppingCartMapper;
import com.lunifer.jo.fpshoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductMapper productMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductMapper productMapper, ShoppingCartMapper shoppingCartMapper){
        this.shoppingCartRepository = shoppingCartRepository;
        this.productMapper = productMapper;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @Override
    public ShoppingCartDTO getShoppingCartById(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).orElse(null);
        return ShoppingCartMapper.INSTANCE.shoppingCartEntityToShoppingCartDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart newShoppingCart = ShoppingCartMapper.INSTANCE.shoppingCartDTOToShoppingCartEntity(shoppingCartDTO);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(newShoppingCart);
        return ShoppingCartMapper.INSTANCE.shoppingCartEntityToShoppingCartDTO(savedShoppingCart);
    }

    @Override
    public ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDTO, Long cartId) {
        // 1. Check whether the shopping cart with the given ID exists in DB or not
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(cartId).orElseThrow();

        // 2. Map the updated fields from shoppingCartDTO to the existing shoppingCartEntity
        existingShoppingCart.setProductList(
                shoppingCartDTO.getProductList().stream()
                        .map(productMapper::productDTOToProductEntity)
                        .collect(Collectors.toList())
        );
        existingShoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());

        // 3. Save the updated shoppingCartEntity back to the database
        ShoppingCart updatedShoppingCartEntity = shoppingCartRepository.save(existingShoppingCart);

        // 4. Map the updated shoppingCartEntity to a ShoppingCartDTO and return it
        return shoppingCartMapper.shoppingCartEntityToShoppingCartDTO(updatedShoppingCartEntity);
    }



    @Override
    public void deleteShoppingCart(Long cartId) {
        shoppingCartRepository.deleteById(cartId);
    }

    @Override
    public List<ShoppingCartDTO> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        return shoppingCarts.stream()
                .map(ShoppingCartMapper.INSTANCE::shoppingCartEntityToShoppingCartDTO)
                .collect(Collectors.toList());
    }
}
