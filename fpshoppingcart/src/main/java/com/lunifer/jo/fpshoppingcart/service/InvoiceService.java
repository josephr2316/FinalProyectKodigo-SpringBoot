package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.CreateInvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
    InvoiceDTO getInvoiceById(Long id);
    PagedResponse<InvoiceDTO> getAllInvoices(Pageable pageable);
    InvoiceDTO createInvoice(CreateInvoiceDTO dto);
    void deleteInvoice(Long id);
}
