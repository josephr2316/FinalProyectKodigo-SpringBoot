package com.lunifer.jo.fpshoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InvoiceDTO {
    private Long invoiceId;
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDate issueDate;
}
