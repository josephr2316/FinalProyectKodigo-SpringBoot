package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Test saveCategory method")
    @Test
    public void testSaveCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category"); // Added to avoid IllegalArgumentException
        Category categoryEntityToSave = new Category();
        Category savedCategoryEntity = new Category();

        // Set up mocks
        when(categoryMapper.categoryDTOToCategoryEntity(categoryDTO)).thenReturn(categoryEntityToSave);
        when(categoryRepository.save(categoryEntityToSave)).thenReturn(savedCategoryEntity);
        when(categoryMapper.categoryEntityToCategoryDTO(savedCategoryEntity)).thenReturn(categoryDTO);

        // Call the method and verify the result
        CategoryDTO result = categoryService.saveCategory(categoryDTO);

        // Verify that methods are called correctly
        verify(categoryMapper).categoryDTOToCategoryEntity(categoryDTO);
        verify(categoryRepository).save(categoryEntityToSave);
        verify(categoryMapper).categoryEntityToCategoryDTO(savedCategoryEntity);

        // Verify the result
        assertThat(result).isEqualTo(categoryDTO);
    }

    @DisplayName("Test getAllCategories method")
    @Test
    public void testGetAllCategories() {
        List<Category> categories = Collections.singletonList(new Category());
        List<CategoryDTO> expectedDTOs = Collections.singletonList(new CategoryDTO());

        // Set up mocks
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.categoryEntityToCategoryDTO(any(Category.class))).thenReturn(new CategoryDTO());

        // Call the method and verify the result
        Set<CategoryDTO> result = categoryService.getAllCategories();

        // Verify that methods are called correctly
        verify(categoryRepository).findAll();
        verify(categoryMapper, times(categories.size())).categoryEntityToCategoryDTO(any(Category.class));

        // Verify the result
        assertThat(result).isEqualTo(expectedDTOs);
    }

    @DisplayName("Test getCategoryById method")
    @Test
    public void testGetCategoryById() {
        long categoryId = 1L;
        Category category = new Category();
        CategoryDTO expectedDTO = new CategoryDTO();

        // Set up mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryEntityToCategoryDTO(category)).thenReturn(expectedDTO);

        // Call the method and verify the result
        CategoryDTO result = categoryService.getCategoryById(categoryId);

        // Verify that methods are called correctly
        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).categoryEntityToCategoryDTO(category);

        // Verify the result
        assertThat(result).isEqualTo(expectedDTO);
    }

    @DisplayName("Test updateCategory method")
    @Test
    public void testUpdateCategory() {
        long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Updated Category");
        Set<ProductDTO> productList = new HashSet<>();
        categoryDTO.setProductList(productList);
        Category existingCategory = new Category();
        existingCategory.setCategoryName("Old Category");
        Category updatedCategory = new Category();

        // Set up mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        when(categoryMapper.categoryEntityToCategoryDTO(updatedCategory)).thenReturn(categoryDTO);

        // Call the method and verify the result
        CategoryDTO result = categoryService.updateCategory(categoryDTO, categoryId);

        // Verify that methods are called correctly
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).save(existingCategory);
        verify(categoryMapper).categoryEntityToCategoryDTO(updatedCategory);

        // Verify the result
        assertThat(result).isEqualTo(categoryDTO);
    }

    @DisplayName("Test deleteCategory method")
    @Test
    public void testDeleteCategory() {
        long categoryId = 1L;
        Category existingCategory = new Category();

        // Set up mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        // Call the method
        categoryService.deleteCategory(categoryId);

        // Verify that methods are called correctly
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }
}
