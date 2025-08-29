package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.config.TestSecurityConfig;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindActiveProducts() {
        // Given
        Category category = new Category();
        category.setCategoryName("Electronics");
        category.setDescription("Electronic products");
        category.setActive(true);
        categoryRepository.save(category);

        Product activeProduct = new Product();
        activeProduct.setProductName("Laptop");
        activeProduct.setDescription("Gaming laptop");
        activeProduct.setPrice(new BigDecimal("999.99"));
        activeProduct.setStock(10);
        activeProduct.setActive(true);
        activeProduct.setCategory(category);
        
        Product inactiveProduct = new Product();
        inactiveProduct.setProductName("Old Phone");
        inactiveProduct.setDescription("Discontinued phone");
        inactiveProduct.setPrice(new BigDecimal("199.99"));
        inactiveProduct.setStock(0);
        inactiveProduct.setActive(false);
        inactiveProduct.setCategory(category);

        productRepository.save(activeProduct);
        productRepository.save(inactiveProduct);

        // When
        Page<Product> activeProducts = productRepository.findAllByActiveTrue(PageRequest.of(0, 10));

        // Then
        assertThat(activeProducts.getContent()).hasSize(1);
        assertThat(activeProducts.getContent().get(0).getProductName()).isEqualTo("Laptop");
        assertThat(activeProducts.getContent().get(0).isActive()).isTrue();
    }

    @Test
    void shouldFindProductsByCategory() {
        // Given
        Category category1 = new Category();
        category1.setCategoryName("Books");
        category1.setDescription("Book category");
        category1.setActive(true);
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setCategoryName("Games");
        category2.setDescription("Game category");
        category2.setActive(true);
        categoryRepository.save(category2);

        Product book = new Product();
        book.setProductName("Java Book");
        book.setDescription("Programming book");
        book.setPrice(new BigDecimal("29.99"));
        book.setStock(5);
        book.setActive(true);
        book.setCategory(category1);
        
        Product game = new Product();
        game.setProductName("Board Game");
        game.setDescription("Strategy game");
        game.setPrice(new BigDecimal("39.99"));
        game.setStock(3);
        game.setActive(true);
        game.setCategory(category2);

        productRepository.save(book);
        productRepository.save(game);

        // When
        Page<Product> booksPage = productRepository.findAllByCategory_CategoryIdAndActiveTrue(category1.getCategoryId(), PageRequest.of(0, 10));

        // Then
        assertThat(booksPage.getContent()).hasSize(1);
        assertThat(booksPage.getContent().get(0).getProductName()).isEqualTo("Java Book");
        assertThat(booksPage.getContent().get(0).getCategory().getCategoryName()).isEqualTo("Books");
    }
}