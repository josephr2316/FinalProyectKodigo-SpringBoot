package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.CreateCategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.dto.UpdateCategoryDTO;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDTO getCategoryById(Long id);
    PagedResponse<CategoryDTO> getAllCategories(Pageable pageable);
    CategoryDTO createCategory(CreateCategoryDTO dto);
    CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto);
    void deleteCategory(Long id);

}
