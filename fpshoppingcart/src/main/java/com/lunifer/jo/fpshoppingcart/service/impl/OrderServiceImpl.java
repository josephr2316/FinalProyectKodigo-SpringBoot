package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return OrderMapper.INSTANCE.orderEntityToOrderDTO(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order newOrder = OrderMapper.INSTANCE.orderDTOToOrderEntity(orderDTO);
        Order savedOrder = orderRepository.save(newOrder);
        return OrderMapper.INSTANCE.orderEntityToOrderDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (existingOrder != null) {
            // Convert the list of ProductDTO to Product using OrderMapper
            List<Product> productList = orderDTO.getProductList().stream()
                    .map(productDTO -> OrderMapper.INSTANCE.mapToProductEntity(productDTO))
                    .collect(Collectors.toList());

            // Update products
            existingOrder.setProductList(productList);

            // Update order date
            existingOrder.setOrderDate(orderDTO.getOrderDate());

            // Update status
            existingOrder.setStatus(orderDTO.getStatus());

            Order updatedOrder = orderRepository.save(existingOrder);
            return OrderMapper.INSTANCE.orderEntityToOrderDTO(updatedOrder);
        } else {
            return null;
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public OrderResponse getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Retrieve a page of orders
        Page<Order> orderList = orderRepository.findAll(pageable);

        // Get content for page object
        List<Order> listOfOrder = orderList.getContent();

        List<OrderDTO> content = listOfOrder.stream()
                .map(OrderMapper.INSTANCE::orderEntityToOrderDTO)
                .collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orderList.getNumber());
        orderResponse.setPageSize(orderList.getSize());
        orderResponse.setTotalElements(orderList.getTotalElements());
        orderResponse.setTotalPages(orderList.getTotalPages());
        orderResponse.setLast(orderList.isLast());
        return orderResponse;
    }
}
