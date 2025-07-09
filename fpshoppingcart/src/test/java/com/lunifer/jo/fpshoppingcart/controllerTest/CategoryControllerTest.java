package com.lunifer.jo.fpshoppingcart.controllerTest;

import com.lunifer.jo.fpshoppingcart.controller.CategoryController;
import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAllCategories method")
    void getAllCategories() {
            Set<CategoryDTO> dummyCategories = new HashSet<>(Arrays.asList(new CategoryDTO(), new CategoryDTO()));
        when(categoryService.getAllCategories()).thenReturn(dummyCategories);


        ResponseEntity<Set<CategoryDTO>> responseEntity = categoryController.getAllCategories();



        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dummyCategories, responseEntity.getBody());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    @DisplayName("Test createCategory method")
    void createCategory() {
        // Arrange
        CategoryDTO requestCategory = new CategoryDTO();
        CategoryDTO createdCategory = new CategoryDTO();
        when(categoryService.saveCategory(requestCategory)).thenReturn(createdCategory);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.createCategory(requestCategory);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdCategory, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test updateCategory method")
    void updateCategory() {
        // Arrange
        long categoryId = 1L;
        CategoryDTO requestCategory = new CategoryDTO();
        CategoryDTO updatedCategory = new CategoryDTO();
        when(categoryService.updateCategory(requestCategory, categoryId)).thenReturn(updatedCategory);

        // Act
        ResponseEntity<CategoryDTO> responseEntity = categoryController.updateCategory(categoryId, requestCategory);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCategory, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test deleteCategory method")
    void deleteCategory() {
        // Arrange
        long categoryId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}
