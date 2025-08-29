package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.config.TestSecurityConfig;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindActiveCategories() {
        // Given
        Category activeCategory = new Category();
        activeCategory.setCategoryName("Electronics");
        activeCategory.setDescription("Electronic products");
        activeCategory.setActive(true);
        
        Category inactiveCategory = new Category();
        inactiveCategory.setCategoryName("Discontinued");
        inactiveCategory.setDescription("Old products");
        inactiveCategory.setActive(false);

        entityManager.persistAndFlush(activeCategory);
        entityManager.persistAndFlush(inactiveCategory);

        // When
        Category foundCategory = categoryRepository.findByCategoryName("Electronics").orElse(null);

        // Then
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getCategoryName()).isEqualTo("Electronics");
        assertThat(foundCategory.isActive()).isTrue();
    }

    @Test
    void shouldFindCategoryByCategoryName() {
        // Given
        Category category = new Category();
        category.setCategoryName("Books");
        category.setDescription("Book products");
        category.setActive(true);
        entityManager.persistAndFlush(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findByCategoryName("Books");

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCategoryName()).isEqualTo("Books");
        assertThat(foundCategory.get().getDescription()).isEqualTo("Book products");
    }
}
