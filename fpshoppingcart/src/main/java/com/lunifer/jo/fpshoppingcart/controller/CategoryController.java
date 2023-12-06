package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService, ReviewService reviewService, OrderService orderService, ShoppingCartService shoppingCartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/disable/{categoryId}")
    public ResponseEntity<String> disableEnableCategory(@PathVariable Long categoryId) {
        // Call the service method to disable the category and get the result message
        String resultMessage = categoryService.disableEnableCategory(categoryId);

        // Check if the operation was successful
        if (resultMessage != null) {
            // If successful, return a response with HTTP status 200 (OK) and the message in the body
            return ResponseEntity.ok(resultMessage);
        } else {
            // If the category was not found, return a response with HTTP status 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {

       List<ProductDTO> products = productService.findAllProductsByCategoryId(categoryId);
       reviewService.deleteReviewByProducts(products);
       shoppingCartService.deleteShoppingCartByProducts(products);
       orderService.deleteOrderByProducts(products);
       productService.deleteAllByCategoryId(categoryId);
       categoryService.deleteCategory(categoryId);
       return ResponseEntity.noContent().build();
    }
}
