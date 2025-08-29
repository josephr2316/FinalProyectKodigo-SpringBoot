package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toInvoiceDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
    }

    @Override
    public PagedResponse<InvoiceDTO> getAllInvoices(Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        return PagedResponse.of(invoices.map(invoiceMapper::toInvoiceDTO));
    }

    @Override
    public InvoiceDTO createInvoice(CreateInvoiceDTO dto) {
        Invoice invoice = invoiceMapper.toInvoice(dto);
        return invoiceMapper.toInvoiceDTO(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        invoiceRepository.delete(invoice);
    }
}