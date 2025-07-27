package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::invoiceEntityToInvoiceDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public PagedResponse<InvoiceDTO> getAllInvoices(Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return PagedResponse.of(invoices.map(invoiceMapper::invoiceEntityToInvoiceDTO));
    }

    @Override
    public InvoiceDTO createInvoice(CreateInvoiceDTO dto) {
        Invoice invoice = invoiceMapper.createInvoiceDTOToInvoice(dto);
        return invoiceMapper.invoiceEntityToInvoiceDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        invoiceRepository.delete(invoice);
    }
}