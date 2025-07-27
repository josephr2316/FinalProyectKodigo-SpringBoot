package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    CategoryDTO getCategoryById(Long id);
    PagedResponse<CategoryDTO> getAllCategories(Pageable pageable);
    CategoryDTO createCategory(CreateCategoryDTO dto);
    CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto);
    void deleteCategory(Long id);

}
