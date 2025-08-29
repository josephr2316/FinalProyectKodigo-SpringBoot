package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.CreateInvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void shouldGetAllInvoices() throws Exception {
        // Given
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1L);
        invoiceDTO.setOrderId(1L);
        invoiceDTO.setTotalAmount(new BigDecimal("99.99"));

        PagedResponse<InvoiceDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(Collections.singletonList(invoiceDTO));
        pagedResponse.setPageNumber(0);
        pagedResponse.setPageSize(10);
        pagedResponse.setTotalElements(1L);
        pagedResponse.setTotalPages(1);

        when(invoiceService.getAllInvoices(any(PageRequest.class))).thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/invoices")
                .param("page", "0")
                .param("size", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invoices retrieved successfully"))
                .andExpect(jsonPath("$.data.content[0].invoiceId").value(1L));
    }

    @Test
    @WithMockUser
    void shouldCreateInvoice() throws Exception {
        // Given
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setOrderId(1L);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1L);
        invoiceDTO.setOrderId(1L);
        invoiceDTO.setTotalAmount(new BigDecimal("99.99"));

        when(invoiceService.createInvoice(any(CreateInvoiceDTO.class))).thenReturn(invoiceDTO);

        // When & Then
        mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createInvoiceDTO))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invoice created successfully"))
                .andExpect(jsonPath("$.data.invoiceId").value(1L));
    }

    @Test
    @WithMockUser
    void shouldGetInvoiceById() throws Exception {
        // Given
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1L);
        invoiceDTO.setOrderId(1L);
        invoiceDTO.setTotalAmount(new BigDecimal("99.99"));

        when(invoiceService.getInvoiceById(1L)).thenReturn(invoiceDTO);

        // When & Then
        mockMvc.perform(get("/api/invoices/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invoice found"))
                .andExpect(jsonPath("$.data.invoiceId").value(1L));
    }
}
