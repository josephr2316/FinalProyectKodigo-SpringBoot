package com.lunifer.jo.fpshoppingcart.service;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;

public interface InvoiceService {
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
    public InvoiceDTO getInvoiceByOrderId(Long orderId);
    public InvoiceDTO updateInvoice(InvoiceDTO invoiceDTO, Long invoiceId);
    public void deleteInvoice(Long invoiceId);
}
