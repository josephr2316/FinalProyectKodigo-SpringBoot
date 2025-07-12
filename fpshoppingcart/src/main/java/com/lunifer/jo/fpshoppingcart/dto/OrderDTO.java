package com.lunifer.jo.fpshoppingcart.dto;

import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data //Incluye getter Setter, constructor vacío, constructor llena, hash, String, equal
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private Set<ProductDTO> productList;
    private LocalDateTime orderDate;
    private OrderStatus status;

}
