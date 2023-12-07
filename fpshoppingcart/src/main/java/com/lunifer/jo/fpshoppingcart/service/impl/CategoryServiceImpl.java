package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Category;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               ProductMapper productMapper, ProductRepository productRepository, ReviewRepository reviewRepository){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        // Perform validation or transformation logic before saving to the database
        validateCategoryDTO(categoryDTO); // Your custom validation method

        // Map the DTO to an entity and save it to the database
        Category categoryEntityToSave = categoryMapper.categoryDTOToCategoryEntity(categoryDTO);
        Category savedCategoryEntity = categoryRepository.save(categoryEntityToSave);

        // Map the saved entity back to a DTO and return it
        return categoryMapper.categoryEntityToCategoryDTO(savedCategoryEntity);
    }

    @Override
    @Transactional
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::categoryEntityToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO getCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::categoryEntityToCategoryDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryId) {
        // 1. Check whether the category with the given ID exists in DB or not
        //Throw exception
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        // 2. Map the updated fields from categoryDTO to the existing categoryEntity
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setProductList(categoryDTO.getProductList().stream().map(productMapper::productDTOToProductEntity).collect(Collectors.toList()));
        existingCategory.setActive(categoryDTO.isActive());

        // 3. Save the updated categoryEntity back to the database
        Category updatedCategoryEntity = categoryRepository.save(existingCategory);

        // 4. Map the updated categoryEntity to a ProductDTO and return it
        return categoryMapper.categoryEntityToCategoryDTO(updatedCategoryEntity);
    }

    @Override
    @Transactional
    public void deleteCategory(long categoryId) {
        // Throw exception
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        categoryOptional.ifPresent(category -> {

        for (Product product : category.getProductList()) {
            for (Order order : product.getOrder()) {
                order.getProductList().remove(product);
            }
            // Buscar las revisiones asociadas
            List<Review> reviews = reviewRepository.findReviewsByProductId(product.getProductId());

            // Eliminar las revisiones asociadas
            reviewRepository.deleteAll(reviews);

            productRepository.delete(product);
        }
    });
                categoryRepository.deleteById(categoryId);
    }

    @Transactional
    @Override
    public String disableEnableCategory(long categoryId) {
        // Try to find the category with the given ID in the database
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        // Check if the category was found
        if (optionalCategory.isPresent()) {
            // If the category is found, get its instance
            Category category = optionalCategory.get();

            // Determine the current state of the category before toggling
            boolean isEnabled = category.isActive();

            // Toggle the 'isActive' attribute (change it to the opposite value)
            category.setActive(!isEnabled);

            // Disable all products associated with this category (we don´t call disableProduct method to avoid multiple response messages of disabled product
            if(!category.isActive()){
                category.getProductList().forEach(product -> product.setActive(false));
            }
            // Since we're using @Transactional, changes will be automatically
            // saved to the database when the transaction is committed

            // Return a message indicating whether the category was successfully disabled or enabled
            return "Category: " + category.getCategoryName() + " with ID: " + category.getCategoryId() +
                    " has been successfully " + (isEnabled ? "disabled" : "enabled");
        }else {
            throw new EntityNotFoundException("Cannot find Category with ID " + categoryId);
        }
    }



    public void validateCategoryDTO(CategoryDTO categoryDTO) {

        // Check if category name is not null and not empty
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
    }

}
