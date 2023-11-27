package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.FPShoppingCartApplication;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest(classes = FPShoppingCartApplication.class)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("JUnit test for saving category")
    @Test
    public void givenCategory_whenSaved_thenReturnSavedCategory() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("CategoryTest");
        category.setActive(true);

        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory).isNotNull();

    }


    @DisplayName("JUnit test for updating category")
    @Test
    public void givenCategory_whenUpdated_thenReturnUpdatedCategory() {
        Category category = new Category();
        category.setCategoryName("CategoryTest");
        categoryRepository.save(category);
        category.setCategoryName("Updated CategoryTest");
        categoryRepository.save(category);
        Optional<Category> retrievedCategory = categoryRepository.findById(category.getCategoryId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getCategoryName()).isEqualTo("Updated CategoryTest");
    }
    @DisplayName("JUnit test for deleting category")
    @Test
    public void givenCategory_whenDeleted_thenReturnNull() {
        Category category = new Category();
        category.setCategoryName("CategoryTest");
        categoryRepository.save(category);
        categoryRepository.deleteById(category.getCategoryId());
        Optional<Category> retrievedCategory = categoryRepository.findById(category.getCategoryId());
        assertThat(retrievedCategory).isNotPresent();
    }
}
