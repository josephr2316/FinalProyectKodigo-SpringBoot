package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.enums.OrderStatus;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.payload.OrderResponse;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserMapper userMapper, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return mapOrderToDTOWithUser(order);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order newOrder = OrderMapper.INSTANCE.orderDTOToOrderEntity(orderDTO);
        newOrder.setUser(userMapper.userDTOToUserEntity(userService.getUserById(orderDTO.getUserId())));
        Order savedOrder = orderRepository.save(newOrder);

        return mapOrderToDTOWithUser(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (existingOrder != null) {
            // Convert the list of ProductDTO to Product using OrderMapper
            Set<Product> productList = orderDTO.getProductList().stream()
                    .map(OrderMapper.INSTANCE::mapToProductEntity)
                    .collect(Collectors.toSet());

            // Update products
            existingOrder.setProductList(productList);

            // Update order date
            existingOrder.setOrderDate(orderDTO.getOrderDate());

            // Update status
            existingOrder.setStatus(orderDTO.getStatus());

            existingOrder.setUser(userMapper.userDTOToUserEntity(userService.getUserById(orderDTO.getUserId())));

            Order updatedOrder = orderRepository.save(existingOrder);
            return mapOrderToDTOWithUser(updatedOrder);
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
                .map(this::mapOrderToDTOWithUser)
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

    @Transactional
    @Override
    public String cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Check if the order is cancellable (e.g., not already canceled or delivered)
            if (OrderStatus.PENDING.equals(order.getStatus()) || OrderStatus.PROCESSING.equals(order.getStatus())) {
                order.setStatus(OrderStatus.CANCELLED);
                // You may want to update other attributes or perform additional logic here

                // Since we're using @Transactional, changes will be automatically
                // saved to the database when the transaction is committed

                return "Order with ID " + orderId + " has been successfully canceled.";
            } else {
                throw new IllegalStateException("Order with ID " + orderId + " cannot be canceled because it is already in a non-cancellable state.");
            }
        } else {
            throw new EntityNotFoundException("Cannot find order with ID " + orderId);
        }
    }
    @Override
    @Transactional
    public Set<OrderDTO> getAllOrdersByUser(User user) {
        return orderRepository.findOrdersByUser(user)
                .stream()
                .map(this::mapOrderToDTOWithUser)
                .collect(Collectors.toSet());


    }

    @Override
    @Transactional
    public Set<OrderDTO> getAllOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserUserId(userId)
                .stream()
                .map(this::mapOrderToDTOWithUser)
                .collect(Collectors.toSet());
    }
    private OrderDTO mapOrderToDTOWithUser(Order order) {
        OrderDTO orderDTO = orderMapper.orderEntityToOrderDTO(order);
        UserDTO userDTO = userMapper.userEntityToUserDTO(order.getUser());
        orderDTO.setUserId(userDTO.getUserId());
        return orderDTO;
    }
}
