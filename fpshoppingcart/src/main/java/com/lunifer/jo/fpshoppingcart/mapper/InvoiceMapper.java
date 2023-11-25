package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);
    @Mapping(target = "orderId", source = "order.orderId")
    InvoiceDTO invoiceEntityToInvoiceDTO(Invoice invoiceEntity);

    @Mapping(target = "order.orderId", source = "orderId")
    Invoice InvoiceDTOToInvoiceEntity(InvoiceDTO invoiceDTO);
}
