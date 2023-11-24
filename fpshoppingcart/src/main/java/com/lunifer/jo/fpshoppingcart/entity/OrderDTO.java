package com.lunifer.jo.fpshoppingcart.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data //Incluye getter Setter, constructor vacío, constructor llena, hash, String, equal
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private  List<ProductDTO> productList;
    private LocalDateTime orderDate;
    private OrderStatus status;

}
