package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @WithMockUser
    void shouldGetAllCategories() throws Exception {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1L);
        categoryDTO.setCategoryName("Electronics");
        categoryDTO.setDescription("Electronic products");
        categoryDTO.setActive(true);
        categoryDTO.setCreatedAt(LocalDateTime.now());
        categoryDTO.setUpdatedAt(LocalDateTime.now());

        PagedResponse<CategoryDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(Collections.singletonList(categoryDTO));
        pagedResponse.setPageNumber(0);
        pagedResponse.setPageSize(10);
        pagedResponse.setTotalElements(1L);
        pagedResponse.setTotalPages(1);

        when(categoryService.getAllCategories(any(PageRequest.class))).thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/categories")
                .param("page", "0")
                .param("size", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Categories retrieved successfully"))
                .andExpect(jsonPath("$.data.content[0].categoryName").value("Electronics"));
    }

    @Test
    @WithMockUser
    void shouldGetCategoryById() throws Exception {
        // Given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1L);
        categoryDTO.setCategoryName("Electronics");
        categoryDTO.setDescription("Electronic products");
        categoryDTO.setActive(true);

        when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

        // When & Then
        mockMvc.perform(get("/api/categories/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Category found"))
                .andExpect(jsonPath("$.data.categoryName").value("Electronics"));
    }
}
