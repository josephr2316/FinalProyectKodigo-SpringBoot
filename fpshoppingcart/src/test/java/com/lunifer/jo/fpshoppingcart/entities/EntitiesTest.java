package com.lunifer.jo.fpshoppingcart.entities;

import com.lunifer.jo.fpshoppingcart.entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    @Test
    void CategoryTestConstructorWithParameters() {

        Category category = new Category(1, "Electronics", true, new ArrayList<>());

        assertEquals(1, category.getCategoryId());
        assertEquals("Electronics", category.getCategoryName());
        assertEquals(true, category.isActive());
        assertEquals(0, category.getProductList().size());
    }

    @Test
    void CategoryTestDefaultConstructor() {

        Category category = new Category();

        assertEquals(0, category.getCategoryId());
        assertNull(category.getCategoryName());

        assertFalse(category.isActive());

        assertNull(category.getProductList());

    }

    @Test
    void InvoiceTestGettersAndSetters() {

        Invoice invoice = new Invoice();
        Order order = new Order();
        BigDecimal totalAmount = new BigDecimal("100.00");
        LocalDate issueDate = LocalDate.now();

        invoice.setInvoiceId(1L);
        invoice.setOrder(order);
        invoice.setTotalAmount(totalAmount);
        invoice.setIssueDate(issueDate);

        assertEquals(1L, invoice.getInvoiceId());
        assertEquals(order, invoice.getOrder());
        assertEquals(totalAmount, invoice.getTotalAmount());
        assertEquals(issueDate, invoice.getIssueDate());
    }

    @Test
    void InvoiceTestDefaultConstructor() {

        Invoice invoice = new Invoice();

        assertNull(invoice.getInvoiceId());
        assertNull(invoice.getOrder());
        assertNull(invoice.getTotalAmount());
        assertNull(invoice.getIssueDate());
    }

    @Test
    void InvoiceTestConstructorWithParameters() {

        Order order = new Order();
        BigDecimal totalAmount = new BigDecimal("50.00");
        LocalDate issueDate = LocalDate.of(2023, 1, 1);

        Invoice invoice = new Invoice(1L, order, totalAmount, issueDate);

        assertEquals(1L, invoice.getInvoiceId());
        assertEquals(order, invoice.getOrder());
        assertEquals(totalAmount, invoice.getTotalAmount());
        assertEquals(issueDate, invoice.getIssueDate());
    }

    @Test
    void OrderTestGettersAndSetters() {

        Order order = new Order();
        List<Product> productList = new ArrayList<>();
        LocalDateTime orderDate = LocalDateTime.now();
        OrderStatus status = OrderStatus.PENDING;
        User user = new User();

        order.setOrderId(1L);
        order.setProductList(productList);
        order.setOrderDate(orderDate);
        order.setStatus(status);
        order.setUser(user);

        assertEquals(1L, order.getOrderId());
        assertEquals(productList, order.getProductList());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(status, order.getStatus());
        assertEquals(user, order.getUser());
    }

    @Test
    void OrderTestDefaultConstructor() {
        // Arrange & Act
        Order order = new Order();

        // Assert
        assertNull(order.getOrderId());
        assertNull(order.getProductList());
        assertNull(order.getOrderDate());
        assertNull(order.getStatus());
        assertNull(order.getUser());
    }

    @Test
    void OrderTestConstructorWithParameters() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        LocalDateTime orderDate = LocalDateTime.of(2023, 1, 1, 10, 30);
        OrderStatus status = OrderStatus.PROCESSING;
        User user = new User();

        // Act
        Order order = new Order(1L, productList, orderDate, status, user);

        // Assert
        assertEquals(1L, order.getOrderId());
        assertEquals(productList, order.getProductList());
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(status, order.getStatus());
        assertEquals(user, order.getUser());
    }

    @Test
    void ProductTestGettersAndSetters() {
        // Arrange
        Product product = new Product();
        Category category = new Category();
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();

        // Act
        product.setProductId(1L);
        product.setProductName("Laptop");
        product.setDescription("Powerful laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setStock(10);
        product.setActive(true);
        product.setShoppingCart(shoppingCartList);
        product.setOrder(orderList);
        product.setCategory(category);

        // Assert
        assertEquals(1L, product.getProductId());
        assertEquals("Laptop", product.getProductName());
        assertEquals("Powerful laptop", product.getDescription());
        assertEquals(new BigDecimal("999.99"), product.getPrice());
        assertEquals(10, product.getStock());
        assertTrue(product.isActive());
        assertEquals(shoppingCartList, product.getShoppingCart());
        assertEquals(orderList, product.getOrder());
        assertEquals(category, product.getCategory());
    }

    @Test
    void ProductTestDefaultConstructor() {

        Product product = new Product();

        assertEquals(0,product.getProductId());
        assertNull(product.getProductName());
        assertNull(product.getDescription());
        assertNull(product.getPrice());
        assertEquals(0,product.getStock());
        assertFalse(product.isActive());
        assertNull(product.getShoppingCart());
        assertNull(product.getOrder());
        assertNull(product.getCategory());
    }

    @Test
    void ReviewTestGettersAndSetters() {
        // Arrange
        Review review = new Review();
        Product product = new Product();
        User user = new User();

        // Act
        review.setReviewId(1L);
        review.setProduct(product);
        review.setComment("Great product!");
        review.setLikeDislike(true);
        review.setUser(user);

        // Assert
        assertEquals(1L, review.getReviewId());
        assertEquals(product, review.getProduct());
        assertEquals("Great product!", review.getComment());
        assertTrue(review.isLikeDislike());
        assertEquals(user, review.getUser());
    }

    @Test
    void ReviewTestDefaultConstructor() {
        // Arrange & Act
        Review review = new Review();

        // Assert
        assertEquals(0,review.getReviewId());
        assertNull(review.getProduct());
        assertNull(review.getComment());
        assertFalse(review.isLikeDislike());
        assertNull(review.getUser());
    }

    @Test
    void ShoppingCartTestGettersAndSetters() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = new User();
        List<Product> productList = new ArrayList<>();

        // Act
        shoppingCart.setCartId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setProductList(productList);
        shoppingCart.setTotalPrice(new BigDecimal("200.00"));

        // Assert
        assertEquals(1L, shoppingCart.getCartId());
        assertEquals(user, shoppingCart.getUser());
        assertEquals(productList, shoppingCart.getProductList());
        assertEquals(new BigDecimal("200.00"), shoppingCart.getTotalPrice());
    }

    @Test
    void ShoppingCartTestDefaultConstructor() {
        // Arrange & Act
        ShoppingCart shoppingCart = new ShoppingCart();

        // Assert
        assertNull(shoppingCart.getCartId());
        assertNull(shoppingCart.getUser());
        assertNull(shoppingCart.getProductList());
        assertNull(shoppingCart.getTotalPrice());
    }

    @Test
    void UserTestGettersAndSetters() {
        // Arrange
        User user = new User();
        List<String> roles = new ArrayList<>();
        List<Order> orderHistory = new ArrayList<>();
        List<Review> reviewHistory = new ArrayList<>();
        List<ShoppingCart> shoppingCartHistory = new ArrayList<>();

        // Act
        user.setUserId(1L);
        user.setFirstName("Juan");
        user.setLastName("Perez");
        user.setEmail("juan.perez@example.com");
        user.setAddress("123 Main St");
        user.setPhoneNumber("123-456-7890");
        user.setUsername("juanperez");
        user.setPassword("password123");
        user.setRoles(roles);
        user.setActive(true);
        user.setOrderHistory(orderHistory);
        user.setReviewHistory(reviewHistory);
        user.setShoppingCartHistory(shoppingCartHistory);

        // Assert
        assertEquals(1L, user.getUserId());
        assertEquals("Juan", user.getFirstName());
        assertEquals("Perez", user.getLastName());
        assertEquals("juan.perez@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("123-456-7890", user.getPhoneNumber());
        assertEquals("juanperez", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(roles, user.getRoles());
        assertTrue(user.isActive());
        assertEquals(orderHistory, user.getOrderHistory());
        assertEquals(reviewHistory, user.getReviewHistory());
        assertEquals(shoppingCartHistory, user.getShoppingCartHistory());
    }

    @Test
    void UserTestDefaultConstructor() {
        // Arrange & Act
        User user = new User();

        // Assert
        assertNull(user.getUserId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getAddress());
        assertNull(user.getPhoneNumber());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRoles());
        assertFalse(user.isActive());
        assertNull(user.getOrderHistory());
        assertNull(user.getReviewHistory());
        assertNull(user.getShoppingCartHistory());
    }

    @Test
    void UserRolTestEnumConstants() {
        // Assert
        assertEquals("ADMIN", UserRol.ADMIN.name());
        assertEquals("BUYER", UserRol.BUYER.name());
    }

    @Test
    void UserRolTestValues() {
        // Arrange
        UserRol[] roles = UserRol.values();

        // Assert
        assertEquals(2, roles.length);
        assertEquals(UserRol.ADMIN, roles[0]);
        assertEquals(UserRol.BUYER, roles[1]);
    }


}
