package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryEntityToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public PagedResponse<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return PagedResponse.of(categories.map(categoryMapper::categoryEntityToCategoryDTO));
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category category = categoryMapper.createCategoryDTOToCategory(dto);
        return categoryMapper.categoryEntityToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryMapper.updateCategoryFromDTO(dto, category);
        return categoryMapper.categoryEntityToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}