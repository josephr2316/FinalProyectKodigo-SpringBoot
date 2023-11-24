package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import org.modelmapper.ModelMapper;

public interface OrderMapper {

    ModelMapper modelMapper = new ModelMapper();

    static OrderDTO mapToOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        return modelMapper.map(order, OrderDTO.class);
    }

    static Order mapToOrderEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        return modelMapper.map(orderDTO, Order.class);
    }

    static Product mapToProductEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return modelMapper.map(productDTO, Product.class);
    }
}

