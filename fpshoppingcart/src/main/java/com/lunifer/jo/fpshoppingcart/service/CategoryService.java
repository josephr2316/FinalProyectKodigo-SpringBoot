package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    Set<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryId);
    void deleteCategory(long categoryId);
    String disableEnableCategory(long categoryId);

}
