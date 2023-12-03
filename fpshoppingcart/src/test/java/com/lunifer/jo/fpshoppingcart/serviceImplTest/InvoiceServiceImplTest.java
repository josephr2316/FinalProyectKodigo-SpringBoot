//package com.lunifer.jo.fpshoppingcart.serviceImplTest;
//
//import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
//import com.lunifer.jo.fpshoppingcart.entity.Invoice;
//import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
//import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
//import com.lunifer.jo.fpshoppingcart.service.impl.InvoiceServiceImpl;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class InvoiceServiceImplTest {
//
//    @Mock
//    private InvoiceRepository invoiceRepository;
//
//    @Mock
//    private InvoiceMapper invoiceMapper;
//
//    @InjectMocks
//    private InvoiceServiceImpl invoiceService;
//    @Before
//    public void setUp() {
//        invoiceMapper = mock(InvoiceMapper.class);
//        invoiceRepository = mock(InvoiceRepository.class);
//        invoiceService = new InvoiceServiceImpl(invoiceRepository,invoiceMapper);
//    }
//    @Test
//    public void testCreateInvoice() {
//        // Arrange
//        InvoiceDTO inputInvoiceDTO = new InvoiceDTO();
//        Invoice invoiceEntityToSave = new Invoice();
//        Invoice savedInvoiceEntity = new Invoice();
//
//        when(invoiceMapper.InvoiceDTOToInvoiceEntity(inputInvoiceDTO)).thenReturn(invoiceEntityToSave);
//        when(invoiceRepository.save(invoiceEntityToSave)).thenReturn(savedInvoiceEntity);
//        when(invoiceMapper.invoiceEntityToInvoiceDTO(savedInvoiceEntity)).thenReturn(inputInvoiceDTO);
//
//        // Act
//        InvoiceDTO result = invoiceService.createInvoice(inputInvoiceDTO);
//
//        // Assert
//        verify(invoiceMapper).InvoiceDTOToInvoiceEntity(inputInvoiceDTO);
//        verify(invoiceRepository).save(invoiceEntityToSave);
//        verify(invoiceMapper).invoiceEntityToInvoiceDTO(savedInvoiceEntity);
//        assertEquals(inputInvoiceDTO, result);
//    }
//
//    @Test
//    public void testGetInvoiceByOrderId_Exists() {
//        // Arrange
//        Long orderId = 1L;
//        Invoice existingInvoice = new Invoice();
//        InvoiceDTO expectedInvoiceDTO = new InvoiceDTO();
//
//        when(invoiceRepository.findByOrderId(orderId)).thenReturn(existingInvoice);
//        when(invoiceMapper.invoiceEntityToInvoiceDTO(existingInvoice)).thenReturn(expectedInvoiceDTO);
//
//        // Act
//        InvoiceDTO result = invoiceService.getInvoiceByOrderId(orderId);
//
//        // Assert
//        verify(invoiceRepository).findByOrderId(orderId);
//        verify(invoiceMapper).invoiceEntityToInvoiceDTO(existingInvoice);
//        assertEquals(expectedInvoiceDTO, result);
//    }
//
//    @Test
//    public void testGetInvoiceByOrderId_NotExists() {
//        // Arrange
//        Long orderId = 1L;
//
//        when(invoiceRepository.findByOrderId(orderId)).thenReturn(null);
//
//        // Act
//        InvoiceDTO result = invoiceService.getInvoiceByOrderId(orderId);
//
//        // Assert
//        verify(invoiceRepository).findByOrderId(orderId);
//        assertNull(result);
//    }
//
//    @Test
//    public void testUpdateInvoice() {
//        // Arrange
//        Long invoiceId = 1L;
//        InvoiceDTO inputInvoiceDTO = new InvoiceDTO();
//        inputInvoiceDTO.setIssueDate(LocalDate.of(2023,1,1));
//        inputInvoiceDTO.setTotalAmount(BigDecimal.valueOf(100.0));
//
//        Invoice existingInvoice = new Invoice();
//        existingInvoice.setInvoiceId(invoiceId);
//
//        Invoice updatedInvoiceEntity = new Invoice();
//        updatedInvoiceEntity.setInvoiceId(invoiceId);
//        updatedInvoiceEntity.setIssueDate(inputInvoiceDTO.getIssueDate());
//        updatedInvoiceEntity.setTotalAmount(inputInvoiceDTO.getTotalAmount());
//
//        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(existingInvoice));
//        when(invoiceRepository.save(existingInvoice)).thenReturn(updatedInvoiceEntity);
//        when(invoiceMapper.invoiceEntityToInvoiceDTO(updatedInvoiceEntity)).thenReturn(inputInvoiceDTO);
//
//        // Act
//        InvoiceDTO result = invoiceService.updateInvoice(inputInvoiceDTO, invoiceId);
//
//        // Assert
//        verify(invoiceRepository).findById(invoiceId);
//        verify(invoiceRepository).save(existingInvoice);
//        verify(invoiceMapper).invoiceEntityToInvoiceDTO(updatedInvoiceEntity);
//        assertEquals(inputInvoiceDTO, result);
//    }
//
//    @Test
//    public void testUpdateInvoice_NotFound() {
//        // Arrange
//        Long invoiceId = 1L;
//        InvoiceDTO inputInvoiceDTO = new InvoiceDTO();
//
//        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(EntityNotFoundException.class, () -> invoiceService.updateInvoice(inputInvoiceDTO, invoiceId));
//        verify(invoiceRepository).findById(invoiceId);
//        verifyNoMoreInteractions(invoiceRepository);
//        verifyNoInteractions(invoiceMapper);
//    }
//
//    @Test
//    public void testDeleteInvoice_Exists() {
//        // Arrange
//        Long invoiceId = 1L;
//
//        when(invoiceRepository.existsById(invoiceId)).thenReturn(true);
//
//        // Act
//        invoiceService.deleteInvoice(invoiceId);
//
//        // Assert
//        verify(invoiceRepository).existsById(invoiceId);
//        verify(invoiceRepository).deleteById(invoiceId);
//    }
//
//    @Test
//    public void testDeleteInvoice_NotExists() {
//        // Arrange
//        Long invoiceId = 1L;
//
//        when(invoiceRepository.existsById(invoiceId)).thenReturn(false);
//
//        // Act & Assert
//        assertThrows(EntityNotFoundException.class, () -> invoiceService.deleteInvoice(invoiceId));
//        verify(invoiceRepository).existsById(invoiceId);
//        verifyNoMoreInteractions(invoiceRepository);
//    }
//}