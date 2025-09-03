package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void shouldFindReviewsByProduct() {
        // Given
        Category category = new Category();
        category.setCategoryName("Test Category");
        category.setDescription("Test category description");
        category.setActive(true);
        category = entityManager.persistAndFlush(category);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setDescription("Test product description");
        product.setPrice(BigDecimal.valueOf(10.99));
        product.setStock(100);
        product.setCategory(category);
        product.setActive(true);
        product = entityManager.persistAndFlush(product);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.com");
        user.setAddress("123 Test St");
        user.setPhoneNumber("1234567890");
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        user = entityManager.persistAndFlush(user);

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(5);
        review.setComment("Great product!");
        review.setLikeDislike(true);
        entityManager.persistAndFlush(review);

        // When
        Page<Review> reviews = reviewRepository.findByProduct_ProductId(product.getProductId(), PageRequest.of(0, 10));

        // Then
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.getContent()).hasSize(1);
        assertThat(reviews.getContent().get(0).getRating()).isEqualTo(5);
        assertThat(reviews.getContent().get(0).getComment()).isEqualTo("Great product!");
    }

    @Test
    void shouldFindReviewsByUser() {
        // Given
        Category category = new Category();
        category.setCategoryName("Test Category");
        category.setDescription("Test category description");
        category.setActive(true);
        category = entityManager.persistAndFlush(category);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setDescription("Test product description");
        product.setPrice(BigDecimal.valueOf(10.99));
        product.setStock(100);
        product.setCategory(category);
        product.setActive(true);
        product = entityManager.persistAndFlush(product);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.com");
        user.setAddress("123 Test St");
        user.setPhoneNumber("1234567890");
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        user = entityManager.persistAndFlush(user);

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(5);
        review.setComment("Great product!");
        review.setLikeDislike(true);
        entityManager.persistAndFlush(review);

        // When
        Page<Review> reviews = reviewRepository.findByUser_UserId(user.getUserId(), PageRequest.of(0, 10));

        // Then
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.getContent()).hasSize(1);
        assertThat(reviews.getContent().get(0).getRating()).isEqualTo(5);
        assertThat(reviews.getContent().get(0).getComment()).isEqualTo("Great product!");
    }
}
