package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import com.lunifer.jo.fpshoppingcart.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;
    private ShoppingCart shoppingCart;
    private User user;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setCategoryName("CategoryTest");
        category = categoryRepository.save(category);

        shoppingCart = new ShoppingCart();
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        user = new User();
        user = userRepository.save(user);
    }

    @DisplayName("JUnit test for saving review")
    @Test
    public void givenReview_whenSaved_thenReturnSavedReview() {
        Product product = createProduct("Test Product", "Product description", new BigDecimal("100.0"), 10, true);

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setComment("Great product!");
        review.setLikeDislike(true);

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getReviewId()).isNotNull(); //Has a Warning, but seems to work.
    }

    @DisplayName("JUnit test for updating review")
    @Test
    public void givenReview_whenUpdated_thenReturnUpdatedReview() {
        Product product = createProduct("Test Product", "Product description", new BigDecimal("100.0"), 10, true);

        Review review = createReview(product, "Great product!", true);
        review = reviewRepository.save(review);

        review.setComment("Good product, but could be improved");
        review.setLikeDislike(false);

        Review updatedReview = reviewRepository.save(review);

        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getComment()).isEqualTo("Good product, but could be improved");
        assertThat(updatedReview.isLikeDislike()).isFalse();
    }

    @DisplayName("JUnit test for deleting review")
    @Test
    public void givenReview_whenDeleted_thenReviewShouldNotExist() {
        Product product = createProduct("Test Product", "Product description", new BigDecimal("100.0"), 10, true);

        Review review = createReview(product, "Great product!", true);
        review = reviewRepository.save(review);

        reviewRepository.deleteById(review.getReviewId());

        Optional<Review> deletedReview = reviewRepository.findById(review.getReviewId());
        assertThat(deletedReview).isNotPresent();
    }

    private Product createProduct(String productName, String description, BigDecimal price, int stock, boolean isActive) {
        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setActive(isActive);
        product.setCategory(category);
        product.setShoppingCart(shoppingCart);
        return productRepository.save(product);
    }

    private Review createReview(Product product, String comment, boolean likeDislike) { //Warnings for repeated values. Should are more testing scenarios.
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setComment(comment);
        review.setLikeDislike(likeDislike);
        return review;
    }
}
