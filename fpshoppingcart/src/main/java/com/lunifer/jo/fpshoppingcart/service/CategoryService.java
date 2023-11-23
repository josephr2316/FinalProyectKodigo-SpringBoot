package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO CategoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(long CategoryId);
    CategoryDTO updateCategory(CategoryDTO CategoryDTO, long CategoryId);
    void deleteCategory(long CategoryId);
}
