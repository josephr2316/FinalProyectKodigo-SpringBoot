package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Explicarle esto a nico
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return OrderMapper.mapToOrderDTO(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order newOrder = OrderMapper.mapToOrderEntity(orderDTO);
        Order savedOrder = orderRepository.save(newOrder);
        return OrderMapper.mapToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);

        if (existingOrder != null) {
            // Convertir la lista de ProductDTO a Product utilizando el OrderMapper
            List<Product> productList = orderDTO.getProductList().stream()
                    .map(productDTO -> (Product) OrderMapper.mapToProductEntity(productDTO))
                    .toList();
            // Actualizar productos
//            existingOrder.setProductList(productList);

            // Actualizar hora
            existingOrder.setOrderDate(orderDTO.getOrderDate());

            // Actualizar estado
            existingOrder.setStatus(orderDTO.getStatus());

            Order updatedOrder = orderRepository.save(existingOrder);
            return OrderMapper.mapToOrderDTO(updatedOrder);
        } else {
            return null;
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::mapToOrderDTO)
                .collect(Collectors.toList());
    }
}
