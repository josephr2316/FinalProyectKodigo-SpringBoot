package com.lunifer.jo.fpshoppingcart.dtoTest;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.OrderStatus;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DTOTest {

    @InjectMocks
    private UserDTO userDTO;

    @Mock
    private Set<Order> orderHistory;

    @Mock
    private Set<Review> reviewHistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setFirstName("Pedro");
        userDTO.setLastName("Perez");
        userDTO.setEmail("pPerez@example.com");
        userDTO.setAddress("123 Main St");
        userDTO.setPhoneNumber("123-456-7890");
        userDTO.setUserName("pe_pe");
        userDTO.setPassword("password");
        userDTO.setActive(true);
        userDTO.setOrderHistory(orderHistory);
        userDTO.setReviewHistory(reviewHistory);
    }
    @Test
    void testCategoryDTOGettersAndSetters() {

        long categoryId = 1L;
        String categoryName = "Electronics";
        boolean isActive = true;

        Set<ProductDTO> productList = new HashSet<>();
        productList.add(new ProductDTO());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(categoryId);
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setActive(isActive);
        categoryDTO.setProductList(productList);

        assertEquals(categoryId, categoryDTO.getCategoryId());
        assertEquals(categoryName, categoryDTO.getCategoryName());
        assertEquals(isActive, categoryDTO.isActive());
        assertNotNull(categoryDTO.getProductList());
        assertEquals(productList.size(), categoryDTO.getProductList().size());
    }

    @Test
    void testInvoiceDTOGettersAndSetters() {

        Long invoiceId = 1L;
        Long orderId = 101L;
        BigDecimal totalAmount = new BigDecimal("100.50");
        LocalDate issueDate = LocalDate.now();

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(invoiceId);
        invoiceDTO.setOrderId(orderId);
        invoiceDTO.setTotalAmount(totalAmount);
        invoiceDTO.setIssueDate(issueDate);

        assertEquals(invoiceId, invoiceDTO.getInvoiceId());
        assertEquals(orderId, invoiceDTO.getOrderId());
        assertEquals(totalAmount, invoiceDTO.getTotalAmount());
        assertEquals(issueDate, invoiceDTO.getIssueDate());
    }

    @Test
    void testOrderDTOGettersAndSetters() {

        Long orderId = 1L;
        Long userId = 101L;
        Set<ProductDTO> productList = new HashSet<>(Arrays.asList(new ProductDTO(), new ProductDTO()));
        LocalDateTime orderDate = LocalDateTime.now();
        OrderStatus status = OrderStatus.PENDING;

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setUserId(userId);
        orderDTO.setProductList(productList);
        orderDTO.setOrderDate(orderDate);
        orderDTO.setStatus(status);

        assertEquals(orderId, orderDTO.getOrderId());
        assertEquals(userId, orderDTO.getUserId());
        assertNotNull(orderDTO.getProductList());
        assertEquals(productList.size(), orderDTO.getProductList().size());
        assertEquals(orderDate, orderDTO.getOrderDate());
        assertEquals(status, orderDTO.getStatus());
    }

    @Test
    void testProductDTOGettersAndSetters() {

        long productId = 1L;
        String productName = "Test Product";
        String description = "This is a test product";
        BigDecimal price = BigDecimal.valueOf(29.99);
        int stock = 100;
        boolean isActive = true;
        CategoryDTO categoryDTO = new CategoryDTO();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productId);
        productDTO.setProductName(productName);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setStock(stock);
        productDTO.setActive(isActive);
        productDTO.setCategoryId(categoryDTO.getCategoryId());

        assertEquals(productId, productDTO.getProductId());
        assertEquals(productName, productDTO.getProductName());
        assertEquals(description, productDTO.getDescription());
        assertEquals(price, productDTO.getPrice());
        assertEquals(stock, productDTO.getStock());
        assertEquals(isActive, productDTO.isActive());
        assertNotNull(productDTO.getCategoryId());
    }

    @Test
    void testReviewDTOGettersAndSetters() {

        long reviewId = 1L;
        long productId = 2L;
        long userId = 3L;
        String comment = "This is a test review";
        boolean likeDislike = true;

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(reviewId);
        reviewDTO.setProductId(productId);
        reviewDTO.setUserId(userId);
        reviewDTO.setComment(comment);
        reviewDTO.setLikeDislike(likeDislike);

        // Verificar los valores a través de los getters
        assertEquals(reviewId, reviewDTO.getReviewId());
        assertEquals(productId, reviewDTO.getProductId());
        assertEquals(userId, reviewDTO.getUserId());
        assertEquals(comment, reviewDTO.getComment());
        assertEquals(likeDislike, reviewDTO.isLikeDislike());
    }

    @Test
    void testShoppingCartDTOGettersAndSetters() {

        ProductDTO produc1 = new ProductDTO(101L, "Product1", "Description1", BigDecimal.valueOf(19.99), 10, true, 1);
        ProductDTO produc2 = new ProductDTO(102L, "Product2", "Description2", BigDecimal.valueOf(29.99), 5, true, 2);
        Long cartId = 1L;
        Long userId = 2L;
        Set<ProductDTO> productList = new HashSet<>(Arrays.asList(produc1
                ,produc2));
        BigDecimal totalPrice = BigDecimal.valueOf(49.98);

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartId(cartId);
        shoppingCartDTO.setUserId(userId);
        shoppingCartDTO.setProductList(productList);
        shoppingCartDTO.setTotalPrice(totalPrice);

        assertEquals(cartId, shoppingCartDTO.getCartId());
        assertEquals(userId, shoppingCartDTO.getUserId());
        assertEquals(productList, shoppingCartDTO.getProductList());
        assertEquals(totalPrice, shoppingCartDTO.getTotalPrice());
    }

    @Test
    void testUserDTOGettersAndSetters() {

        assertEquals(1L, userDTO.getUserId());
        assertEquals("Pedro", userDTO.getFirstName());
        assertEquals("Perez", userDTO.getLastName());
        assertEquals("pPerez@example.com", userDTO.getEmail());
        assertEquals("123 Main St", userDTO.getAddress());
        assertEquals("123-456-7890", userDTO.getPhoneNumber());
        assertEquals("pe_pe", userDTO.getUserName());
        assertEquals("password", userDTO.getPassword());
        assertEquals(true, userDTO.isActive());
        assertEquals(orderHistory, userDTO.getOrderHistory());
        assertEquals(reviewHistory, userDTO.getReviewHistory());

       userDTO.setFirstName("Maria");
        userDTO.setActive(false);

        assertEquals("Maria", userDTO.getFirstName());
        assertEquals(false, userDTO.isActive());
    }


}


