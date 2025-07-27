package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

        boolean existsByCategoryName(String categoryName);
        Optional<Category> findByCategoryName(String categoryName);
        Optional<Category> findByCategoryId(Long categoryId);
        void deleteByCategoryId(Long categoryId);   
                    
}
