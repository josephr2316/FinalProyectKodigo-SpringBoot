package com.lunifer.jo.fpshoppingcart.mapper;

import com.lunifer.jo.fpshoppingcart.dto.*;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    @Mapping(target = "paymentStatus", source = "paymentStatus")
    InvoiceDTO toInvoiceDTO(Invoice invoice);
}