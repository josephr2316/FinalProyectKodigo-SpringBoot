package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateInvoice() {
        // Arrange
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setTotalAmount(BigDecimal.valueOf(100.0));
        invoiceDTO.setIssueDate(LocalDate.of(2023, 1, 1));
        invoiceDTO.setInvoiceId(1L);
        Invoice savedInvoice = new Invoice();

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(savedInvoice);
        when(invoiceMapper.InvoiceDTOToInvoiceEntity(any(InvoiceDTO.class))).thenReturn(new Invoice());
        when(invoiceMapper.invoiceEntityToInvoiceDTO(any(Invoice.class))).thenReturn(invoiceDTO);

        // Act
        InvoiceDTO result = invoiceService.createInvoice(invoiceDTO);

        // Assert
        verify(invoiceMapper).InvoiceDTOToInvoiceEntity(invoiceDTO);
        verify(invoiceRepository).save(any(Invoice.class));
        verify(invoiceMapper).invoiceEntityToInvoiceDTO(any(Invoice.class));

        assertThat(result).isNotNull();
        assertThat(result.getInvoiceId()).isNotNull();  //
        assertThat(result.getIssueDate()).isEqualTo(invoiceDTO.getIssueDate());
        assertThat(result.getTotalAmount()).isEqualTo(invoiceDTO.getTotalAmount());
    }


    @DisplayName("Test deleteInvoice method")
    @Test
    public void testDeleteInvoice() {
        // Arrange
        Long invoiceId = 1L;
        when(invoiceRepository.existsById(invoiceId)).thenReturn(true);

        // Act
        invoiceService.deleteInvoice(invoiceId);

        // Assert
        verify(invoiceRepository).existsById(invoiceId);
        verify(invoiceRepository).deleteById(invoiceId);
    }

    @DisplayName("Test updateInvoice method")
    @Test
    public void testUpdateInvoice() {
        // Arrange
        Long invoiceId = 1L;
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setIssueDate(LocalDate.parse("2023-01-01"));
        invoiceDTO.setTotalAmount(BigDecimal.valueOf(100.0));

        Invoice existingInvoice = new Invoice();
        existingInvoice.setInvoiceId(invoiceId);
        existingInvoice.setIssueDate(LocalDate.parse("2022-12-01"));
        existingInvoice.setTotalAmount(BigDecimal.valueOf(50.0));

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(existingInvoice));
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(invoiceMapper.invoiceEntityToInvoiceDTO(any(Invoice.class))).thenReturn(invoiceDTO);

        // Act
        InvoiceDTO result = invoiceService.updateInvoice(invoiceDTO, invoiceId);

        // Assert
        verify(invoiceRepository).findById(invoiceId);
        verify(invoiceRepository).save(any(Invoice.class));
        verify(invoiceMapper).invoiceEntityToInvoiceDTO(any(Invoice.class));

        assertThat(result).isNotNull();
        assertThat(result.getIssueDate()).isEqualTo(invoiceDTO.getIssueDate());
        assertThat(result.getTotalAmount()).isEqualTo(invoiceDTO.getTotalAmount());
    }

    @DisplayName("Test getInvoiceByOrderId method")
    @Test
    public void testGetInvoiceByOrderId() {
        // Arrange
        Long orderId = 1L;
        Invoice invoiceEntity = new Invoice();
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1L);

        when(invoiceRepository.findByOrderId(orderId)).thenReturn(invoiceEntity);
        when(invoiceMapper.invoiceEntityToInvoiceDTO(invoiceEntity)).thenReturn(invoiceDTO);

        // Act
        InvoiceDTO result = invoiceService.getInvoiceByOrderId(orderId);

        // Assert
        verify(invoiceRepository).findByOrderId(orderId);
        verify(invoiceMapper).invoiceEntityToInvoiceDTO(invoiceEntity);

        assertThat(result).isEqualTo(invoiceDTO);
    }

//    @DisplayName("Test updateInvoice method with non-existing invoice")
//    @Test
//    public void testUpdateInvoiceNonExisting() {
//        // Arrange
//        Long invoiceId = 1L;
//        InvoiceDTO invoiceDTO = new InvoiceDTO();
//        invoiceDTO.setIssueDate("2023-01-01");
//        invoiceDTO.setTotalAmount(100.0);
//
//        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(EntityNotFoundException.class, () -> invoiceService.updateInvoice(invoiceDTO, invoiceId));
//
//        // Verify that methods are called correctly
//        verify(invoiceRepository).findById(invoiceId);
//        verify(invoiceRepository).save(any(Invoice.class)); // Should not be called
//        verify(invoiceMapper).invoiceEntityToInvoiceDTO(any(Invoice.class)); // Should not be called
//    }
}
