package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.payload.ShoppingCartResponse;
import com.lunifer.jo.fpshoppingcart.repository.ShoppingCartRepository;
import com.lunifer.jo.fpshoppingcart.mapper.ShoppingCartMapper;
import com.lunifer.jo.fpshoppingcart.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.ContentHandler;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @Override
    @Transactional
    public ShoppingCartDTO getShoppingCartById(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingCart", "id", cartId));
        return ShoppingCartMapper.INSTANCE.shoppingCartEntityToShoppingCartDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart newShoppingCart = ShoppingCartMapper.INSTANCE.shoppingCartDTOToShoppingCartEntity(shoppingCartDTO);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(newShoppingCart);
        return ShoppingCartMapper.INSTANCE.shoppingCartEntityToShoppingCartDTO(savedShoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDTO updateShoppingCart(ShoppingCartDTO shoppingCartDTO, Long cartId) {
        // 1. Check whether the shopping cart with the given ID exists in DB or not
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingCart", "id", cartId));

        // 2. Map the updated fields from shoppingCartDTO to the existing shoppingCartEntity
        existingShoppingCart.setProductList(
                shoppingCartDTO.getProductList().stream()
                        .map(ProductMapper.INSTANCE::productDTOToProductEntity)
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
    @Transactional
    public ShoppingCartResponse getAllShoppingCarts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Retrieve a page of shoppingCarts
        Page<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll(pageable);

        // Get content for page object
        List<ShoppingCart> shoppingCartList = shoppingCarts.getContent();

        List<ShoppingCartDTO> content = shoppingCartList.stream()
                .map(ShoppingCartMapper.INSTANCE::shoppingCartEntityToShoppingCartDTO)
                .collect(Collectors.toList());

        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
        shoppingCartResponse.setContent(content);
        shoppingCartResponse.setPageNo(shoppingCarts.getNumber());
        shoppingCartResponse.setPageSize(shoppingCarts.getSize());
        shoppingCartResponse.setTotalElements(shoppingCarts.getTotalElements());
        shoppingCartResponse.setTotalPages(shoppingCarts.getTotalPages());
        shoppingCartResponse.setLast(shoppingCarts.isLast());
        return shoppingCartResponse;
    }
}
