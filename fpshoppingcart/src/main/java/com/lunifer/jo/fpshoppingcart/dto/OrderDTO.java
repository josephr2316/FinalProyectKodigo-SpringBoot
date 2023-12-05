package com.lunifer.jo.fpshoppingcart.dto;

import com.lunifer.jo.fpshoppingcart.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data //Incluye getter Setter, constructor vacío, constructor llena, hash, String, equal
@Builder
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private  List<ProductDTO> productList;
    private LocalDateTime orderDate;
    private OrderStatus status;

}
