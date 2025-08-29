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
import java.util.List;
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
        category.setCategoryName("Electronics");
        category.setDescription("Electronic devices");
        category.setActive(true);
        
        Category savedCategory = entityManager.persistAndFlush(category);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStock(10);
        product.setActive(true);
        product.setCategory(savedCategory);
        
        Product savedProduct = entityManager.persistAndFlush(product);

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        User savedUser = entityManager.persistAndFlush(user);

        Review review = new Review();
        review.setProduct(savedProduct);
        review.setUser(savedUser);
        review.setComment("Great product!");
        review.setRating(5);
        review.setLikeDislike(true);
        
        entityManager.persistAndFlush(review);

        // When
        Page<Review> foundReviews = reviewRepository.findByProduct_ProductId(
            savedProduct.getProductId(), 
            PageRequest.of(0, 10)
        );

        // Then
        assertThat(foundReviews.getContent()).hasSize(1);
        assertThat(foundReviews.getContent().get(0).getProduct().getProductId())
            .isEqualTo(savedProduct.getProductId());
        assertThat(foundReviews.getContent().get(0).getRating()).isEqualTo(5);
    }

    @Test
    void shouldFindReviewsByUser() {
        // Given
        Category category = new Category();
        category.setCategoryName("Electronics");
        category.setDescription("Electronic devices");
        category.setActive(true);
        
        Category savedCategory = entityManager.persistAndFlush(category);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStock(10);
        product.setActive(true);
        product.setCategory(savedCategory);
        
        Product savedProduct = entityManager.persistAndFlush(product);

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setActive(true);
        user.setRoles(Set.of(UserRol.USER));
        
        User savedUser = entityManager.persistAndFlush(user);

        Review review = new Review();
        review.setProduct(savedProduct);
        review.setUser(savedUser);
        review.setComment("Great product!");
        review.setRating(5);
        review.setLikeDislike(true);
        
        entityManager.persistAndFlush(review);

        // When
        Page<Review> foundReviewsPage = reviewRepository.findByUser_UserId(savedUser.getUserId(), PageRequest.of(0, 10));

        // Then
        assertThat(foundReviewsPage.getContent()).hasSize(1);
        assertThat(foundReviewsPage.getContent().get(0).getUser().getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(foundReviewsPage.getContent().get(0).getRating()).isEqualTo(5);
    }

    @Test
    void shouldReturnEmptyListWhenNoReviewsFound() {
        // When
        Page<Review> reviewsPage = reviewRepository.findByUser_UserId(999L, PageRequest.of(0, 10));

        // Then
        assertThat(reviewsPage.getContent()).isEmpty();
    }
}
