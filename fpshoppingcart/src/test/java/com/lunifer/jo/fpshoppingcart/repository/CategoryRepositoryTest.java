package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.FPShoppingCartApplication;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

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
        //category.setCategoryId(1);
        category.setCategoryName("CategoryTest");
        category.setActive(true);

        Category savedCategory = categoryRepository.save(category);
        //assertThat(savedCategory).isNotNull();

    }
}
