package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Override
    public PagedResponse<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return PagedResponse.of(categories.map(categoryMapper::toCategoryDTO));
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category category = categoryMapper.toCategory(dto);
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryMapper.updateCategoryFromDTO(dto, category);
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }
}