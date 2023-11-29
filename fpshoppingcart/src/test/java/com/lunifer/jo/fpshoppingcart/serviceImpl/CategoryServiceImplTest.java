package com.lunifer.jo.fpshoppingcart.serviceImpl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;



    @Before
    public void setUp() {
        initMocks(this);
    }
    @Test
    public void testConstructorInitialization() {
        // Mocks
        CategoryRepository categoryRepositoryMock = mock(CategoryRepository.class);
        CategoryMapper categoryMapperMock = mock(CategoryMapper.class);
        ProductMapper productMapperMock = mock(ProductMapper.class);

        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepositoryMock, categoryMapperMock, productMapperMock);

        assertNotNull(categoryService.getCategoryRepository());
        assertNotNull(categoryService.getCategoryMapper());
        assertNotNull(categoryService.getProductMapper());
    }

    @Test
    public void testSaveCategory() {

        CategoryDTO inputCategoryDTO = new CategoryDTO();
        inputCategoryDTO.setCategoryName("Test Category");
        inputCategoryDTO.setActive(true);

        Category categoryEntityToSave = new Category();
        categoryEntityToSave.setCategoryName("Test Category");
        categoryEntityToSave.setActive(true);

        Category savedCategoryEntity = new Category();
        savedCategoryEntity.setCategoryName("Test Category");
        savedCategoryEntity.setActive(true);

        when(categoryMapper.categoryDTOToCategoryEntity(inputCategoryDTO)).thenReturn(categoryEntityToSave);
        when(categoryRepository.save(categoryEntityToSave)).thenReturn(savedCategoryEntity);
        when(categoryMapper.categoryEntityToCategoryDTO(savedCategoryEntity)).thenReturn(inputCategoryDTO);

        CategoryDTO result = categoryService.saveCategory(inputCategoryDTO);

        verify(categoryMapper).categoryDTOToCategoryEntity(inputCategoryDTO);
        verify(categoryRepository).save(categoryEntityToSave);
        verify(categoryMapper).categoryEntityToCategoryDTO(savedCategoryEntity);

        assertEquals(inputCategoryDTO, result);
    }

    @Test
    public void testGetAllCategories() {

        List<Category> categoryEntities = Arrays.asList(
                new Category(1L, "Category1", true, null),
                new Category(2L, "Category2", true, null)
        );

        List<CategoryDTO> expectedCategoryDTOs = categoryEntities.stream()
                .map(categoryMapper::categoryEntityToCategoryDTO)
                .collect(Collectors.toList());

        when(categoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryMapper.categoryEntityToCategoryDTO(categoryEntities.get(0))).thenReturn(expectedCategoryDTOs.get(0));
        when(categoryMapper.categoryEntityToCategoryDTO(categoryEntities.get(1))).thenReturn(expectedCategoryDTOs.get(1));

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(expectedCategoryDTOs, result);
    }

    @Test
    public void testGetCategoryById() {
        // Arrange
        long categoryIdToFind = 1L;
        Category categoryEntity = new Category();
        categoryEntity.setCategoryId(categoryIdToFind);
        categoryEntity.setCategoryName("Test Category");
        categoryEntity.setActive(true);
        categoryEntity.setProductList(null);

        CategoryDTO expectedCategoryDTO = new CategoryDTO();
        expectedCategoryDTO.setCategoryId(categoryIdToFind);
        expectedCategoryDTO.setCategoryName("Test Category");
        expectedCategoryDTO.setActive(true);
        expectedCategoryDTO.setProductList(null);  // Ajusta esto según tus necesidades

        when(categoryRepository.findById(categoryIdToFind)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.categoryEntityToCategoryDTO(categoryEntity)).thenReturn(expectedCategoryDTO);

        CategoryDTO result = categoryService.getCategoryById(categoryIdToFind);

        assertEquals(expectedCategoryDTO, result);
    }
    @Test
    public void testUpdateCategory() {

        long categoryIdToUpdate = 1L;
        CategoryDTO inputCategoryDTO = new CategoryDTO();
        inputCategoryDTO.setCategoryName("Updated Category");
        inputCategoryDTO.setActive(false);
        inputCategoryDTO.setProductList(Collections.emptyList());

        Category existingCategory = new Category();
        existingCategory.setCategoryId(categoryIdToUpdate);
        existingCategory.setCategoryName("Original Category");
        existingCategory.setActive(true);
        existingCategory.setProductList(Collections.emptyList());

        Category updatedCategoryEntity = new Category();
        updatedCategoryEntity.setCategoryId(categoryIdToUpdate);
        updatedCategoryEntity.setCategoryName("Updated Category");
        updatedCategoryEntity.setActive(false);
        updatedCategoryEntity.setProductList(Collections.emptyList());


        when(categoryRepository.findById(categoryIdToUpdate)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(Mockito.any())).thenReturn(updatedCategoryEntity);

        when(categoryMapper.categoryEntityToCategoryDTO(updatedCategoryEntity)).thenReturn(inputCategoryDTO);

        CategoryDTO result = categoryService.updateCategory(inputCategoryDTO, categoryIdToUpdate);

        assertEquals(inputCategoryDTO, result);
    }

    @Test
    public void testDeleteCategory() {

        long categoryIdToDelete = 1L;

        Category existingCategory = new Category();
        existingCategory.setCategoryId(categoryIdToDelete);
        existingCategory.setCategoryName("Test Category");
        existingCategory.setActive(true);

        when(categoryRepository.findById(categoryIdToDelete)).thenReturn(Optional.of(existingCategory));

        categoryService.deleteCategory(categoryIdToDelete);

        verify(categoryRepository).findById(categoryIdToDelete);
        verify(categoryRepository).deleteById(categoryIdToDelete);
    }
    @Test
    public void testValidateCategoryDTO_Valid() {

        CategoryDTO validCategoryDTO = new CategoryDTO();
        validCategoryDTO.setCategoryName("Valid Category");

        assertDoesNotThrow(() -> categoryService.validateCategoryDTO(validCategoryDTO));
    }

    @Test
    public void testValidateCategoryDTO_NullName() {
        CategoryDTO invalidCategoryDTO = new CategoryDTO();
        invalidCategoryDTO.setCategoryName(null);

        assertThrows(IllegalArgumentException.class, () -> categoryService.validateCategoryDTO(invalidCategoryDTO));
    }

    @Test
    public void testValidateCategoryDTO_EmptyName() {

        CategoryDTO invalidCategoryDTO = new CategoryDTO();
        invalidCategoryDTO.setCategoryName("");

       assertThrows(IllegalArgumentException.class, () -> categoryService.validateCategoryDTO(invalidCategoryDTO));
    }

    @Test
    public void testValidateCategoryDTO_WhitespaceName() {

        CategoryDTO invalidCategoryDTO = new CategoryDTO();
        invalidCategoryDTO.setCategoryName("   ");

        assertThrows(IllegalArgumentException.class, () -> categoryService.validateCategoryDTO(invalidCategoryDTO));
    }

}
