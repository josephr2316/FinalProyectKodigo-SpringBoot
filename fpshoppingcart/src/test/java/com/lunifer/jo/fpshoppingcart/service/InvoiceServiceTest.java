package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateInvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.enums.PaymentStatus;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;
    private InvoiceDTO invoiceDTO;
    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");

        order = new Order();
        order.setOrderId(1L);
        order.setUser(user);

        invoice = new Invoice();
        invoice.setInvoiceId(1L);
        invoice.setOrder(order);
        invoice.setInvoiceNumber("INV-001");
        invoice.setTotalAmount(new BigDecimal("999.99"));
        invoice.setPaymentStatus(PaymentStatus.PAID);
        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1L);
        invoiceDTO.setOrderId(1L);
        invoiceDTO.setInvoiceNumber("INV-001");
        invoiceDTO.setTotalAmount(new BigDecimal("999.99"));
        invoiceDTO.setPaymentStatus("PAID");
    }

    @Test
    void shouldGetInvoiceById() {
        // Given
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(invoiceDTO);

        // When
        InvoiceDTO result = invoiceService.getInvoiceById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInvoiceId()).isEqualTo(1L);
        assertThat(result.getInvoiceNumber()).isEqualTo("INV-001");
        assertThat(result.getTotalAmount()).isEqualTo(new BigDecimal("999.99"));
        assertThat(result.getPaymentStatus()).isEqualTo("PAID");
        verify(invoiceRepository).findById(1L);
        verify(invoiceMapper).toInvoiceDTO(invoice);
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {
        // Given
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> invoiceService.getInvoiceById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invoice")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(invoiceRepository).findById(1L);
        verify(invoiceMapper, never()).toInvoiceDTO(any());
    }

    @Test
    void shouldGetAllInvoices() {
        // Given
        Page<Invoice> invoicePage = new PageImpl<>(Collections.singletonList(invoice));
        when(invoiceRepository.findAll(any(PageRequest.class))).thenReturn(invoicePage);
        when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(invoiceDTO);

        // When
        PagedResponse<InvoiceDTO> result = invoiceService.getAllInvoices(PageRequest.of(0, 10));

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getInvoiceNumber()).isEqualTo("INV-001");
        verify(invoiceRepository).findAll(any(PageRequest.class));
        verify(invoiceMapper).toInvoiceDTO(invoice);
    }

    @Test
    void shouldCreateInvoice() {
        // Given
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setOrderId(1L);
        createInvoiceDTO.setTotalAmount(new BigDecimal("1299.99"));

        when(invoiceMapper.toInvoice(createInvoiceDTO)).thenReturn(invoice);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        when(invoiceMapper.toInvoiceDTO(invoice)).thenReturn(invoiceDTO);

        // When
        InvoiceDTO result = invoiceService.createInvoice(createInvoiceDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInvoiceNumber()).isEqualTo("INV-001");
        verify(invoiceMapper).toInvoice(createInvoiceDTO);
        verify(invoiceRepository).save(any(Invoice.class));
        verify(invoiceMapper).toInvoiceDTO(invoice);
    }

    @Test
    void shouldDeleteInvoice() {
        // Given
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        // When
        invoiceService.deleteInvoice(1L);

        // Then
        verify(invoiceRepository).findById(1L);
        verify(invoiceRepository).delete(invoice);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentInvoice() {
        // Given
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> invoiceService.deleteInvoice(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invoice")
                .hasMessageContaining("id")
                .hasMessageContaining("1");
        verify(invoiceRepository).findById(1L);
        verify(invoiceRepository, never()).delete(any());
    }
}
