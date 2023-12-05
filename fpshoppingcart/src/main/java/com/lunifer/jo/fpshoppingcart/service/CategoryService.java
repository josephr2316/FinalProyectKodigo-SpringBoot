package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryId);
    void deleteCategory(long categoryId);
    String disableEnableCategory(long categoryId);

}
