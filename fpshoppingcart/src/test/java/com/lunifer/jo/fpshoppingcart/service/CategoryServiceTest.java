package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.CreateCategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.UpdateCategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Electronics");
        category.setDescription("Electronic products");
        category.setActive(true);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1L);
        categoryDTO.setCategoryName("Electronics");
        categoryDTO.setDescription("Electronic products");
        categoryDTO.setActive(true);
    }

    @Test
    void shouldGetCategoryById() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        // When
        CategoryDTO result = categoryService.getCategoryById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCategoryId()).isEqualTo(1L);
        assertThat(result.getCategoryName()).isEqualTo("Electronics");
        verify(categoryRepository).findById(1L);
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoryService.getCategoryById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(categoryRepository).findById(1L);
        verify(categoryMapper, never()).toCategoryDTO(any());
    }

    @Test
    void shouldGetAllCategories() {
        // Given
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        // When
        PagedResponse<CategoryDTO> result = categoryService.getAllCategories(PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCategoryName()).isEqualTo("Electronics");
        verify(categoryRepository).findAll(any(PageRequest.class));
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    void shouldCreateCategory() {
        // Given
        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        createCategoryDTO.setCategoryName("Books");
        createCategoryDTO.setDescription("Book products");

        when(categoryMapper.toCategory(createCategoryDTO)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        // When
        CategoryDTO result = categoryService.createCategory(createCategoryDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCategoryName()).isEqualTo("Electronics");
        verify(categoryMapper).toCategory(createCategoryDTO);
        verify(categoryRepository).save(any(Category.class));
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    void shouldUpdateCategory() {
        // Given
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();
        updateCategoryDTO.setCategoryName("Updated Electronics");
        updateCategoryDTO.setDescription("Updated description");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        // When
        CategoryDTO result = categoryService.updateCategory(1L, updateCategoryDTO);

        // Then
        assertThat(result).isNotNull();
        verify(categoryRepository).findById(1L);
        verify(categoryMapper).updateCategoryFromDTO(updateCategoryDTO, category);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toCategoryDTO(category);
    }

    @Test
    void shouldDeleteCategory() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // When
        categoryService.deleteCategory(1L);

        // Then
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).delete(category);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        // Given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(categoryRepository).findById(1L);
        verify(categoryRepository, never()).delete(any());
    }
}
