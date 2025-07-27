package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Order;

import java.util.List;


public interface InvoiceService {
    InvoiceDTO getInvoiceById(Long id);
    PagedResponse<InvoiceDTO> getAllInvoices(Pageable pageable);
    InvoiceDTO createInvoice(CreateInvoiceDTO dto);
    void deleteInvoice(Long id);
}
