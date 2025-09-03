package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        // Create test category
        testCategory = new Category();
        testCategory.setCategoryName("Test Category");
        testCategory.setDescription("Test category description");
        testCategory.setActive(true);
        
        // Persist the category
        testCategory = entityManager.persistAndFlush(testCategory);
    }

    @Test
    void shouldFindActiveCategories() {
        // When
        List<Category> allCategories = categoryRepository.findAll();
        List<Category> activeCategories = allCategories.stream()
                .filter(Category::isActive)
                .toList();

        // Then
        assertThat(activeCategories).isNotEmpty();
        assertThat(activeCategories).anyMatch(category -> 
            category.getCategoryName().equals("Test Category") && category.isActive());
    }

    @Test
    void shouldFindCategoryByCategoryName() {
        // When
        Optional<Category> found = categoryRepository.findByCategoryName("Test Category");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getCategoryName()).isEqualTo("Test Category");
        assertThat(found.get().getDescription()).isEqualTo("Test category description");
    }
}
