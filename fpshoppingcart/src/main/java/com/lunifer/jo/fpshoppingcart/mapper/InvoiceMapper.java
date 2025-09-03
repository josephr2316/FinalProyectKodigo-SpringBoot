package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    @Mapping(target = "paymentStatus", source = "paymentStatus")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "taxAmount", source = "taxAmount")
    @Mapping(target = "discountAmount", source = "discountAmount")
    @Mapping(target = "netAmount", expression = "java(invoice.getNetAmount())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    InvoiceDTO toInvoiceDTO(Invoice invoice);

    @Mapping(target = "invoiceId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Invoice toInvoice(CreateInvoiceDTO dto);
}