package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.FPShoppingCartApplication;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FPShoppingCartApplication.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("JUnit test for saving product")
    @Test
    public void givenProduct_whenSaved_thenReturnSavedProduct() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalPrice(BigDecimal.ZERO);
        shoppingCartRepository.save(shoppingCart);

        Category category = new Category();
        category.setCategoryName("CategoryTest");
        categoryRepository.save(category);

        Product product = new Product();
        product.setProductName("ProductTest");
        product.setDescription("Product description");
        product.setPrice(BigDecimal.TEN);
        product.setStock(100);
        product.setShoppingCart(shoppingCart);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("ProductTest");
    }
    @DisplayName("JUnit test for updating product")
    @Test
    public void givenProduct_whenUpdated_thenReturnUpdatedProduct() {
        // Arrange
        Category category = new Category();
        category.setCategoryName("Clothing");
        categoryRepository.save(category);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);

        Product product = new Product();
        product.setProductName("Shirt");
        product.setDescription("Casual shirt");
        product.setPrice(new BigDecimal("29.99"));
        product.setStock(50);
        product.setCategory(category);
        product.setShoppingCart(shoppingCart);
        productRepository.save(product);

        product.setProductName("Updated Shirt");
        product.setDescription("Stylish shirt");
        product.setPrice(new BigDecimal("39.99"));
        product.setStock(40);
        productRepository.save(product);

        Optional<Product> updatedProduct = productRepository.findById(product.getProductId());
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().getProductName()).isEqualTo("Updated Shirt");
        assertThat(updatedProduct.get().getDescription()).isEqualTo("Stylish shirt");
        assertThat(updatedProduct.get().getPrice()).isEqualTo(new BigDecimal("39.99"));
        assertThat(updatedProduct.get().getStock()).isEqualTo(40);
        assertThat(updatedProduct.get().getCategory().getCategoryName()).isEqualTo(category.getCategoryName());
        assertThat(updatedProduct.get().getShoppingCart().getCartId()).isEqualTo(shoppingCart.getCartId());
    }

    @DisplayName("JUnit test for deleting product")
    @Test
    public void givenProduct_whenDeleted_thenNotFound() {
        // Arrange
        Category category = new Category();
        category.setCategoryName("Books");
        categoryRepository.save(category);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);

        Product product = new Product();
        product.setProductName("Book");
        product.setDescription("Interesting book");
        product.setPrice(new BigDecimal("19.99"));
        product.setStock(20);

        product.setCategory(category);
        product.setShoppingCart(shoppingCart);
        productRepository.save(product);

        // Act
        productRepository.deleteById(product.getProductId());

        // Assert
        Optional<Product> deletedProduct = productRepository.findById(product.getProductId());
        assertThat(deletedProduct).isNotPresent();
    }

}
