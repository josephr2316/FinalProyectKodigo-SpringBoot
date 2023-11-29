package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }


    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.InvoiceDTOToInvoiceEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.invoiceEntityToInvoiceDTO(invoice);
    }

    @Override
    public InvoiceDTO getInvoiceByOrderId(Long orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId);

        if (invoice != null) {
            return invoiceMapper.invoiceEntityToInvoiceDTO(invoice);
        } else {
            return null;
        }
    }

    @Override
    public InvoiceDTO updateInvoice(InvoiceDTO invoiceDTO, Long invoiceId) {

        Invoice existingInvoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la factura #: " + invoiceId));


        existingInvoice.setTotalAmount(invoiceDTO.getTotalAmount());

        Invoice updatedInvoiceEntity = invoiceRepository.save(existingInvoice);

        return invoiceMapper.invoiceEntityToInvoiceDTO(updatedInvoiceEntity);
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        if (invoiceRepository.existsById(invoiceId)) {
            invoiceRepository.deleteById(invoiceId);
        } else {

            throw new EntityNotFoundException("No se encontró la factura #: " + invoiceId);
        }

    }
}
