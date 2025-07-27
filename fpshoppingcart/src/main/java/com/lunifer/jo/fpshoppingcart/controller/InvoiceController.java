package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(invoiceService.getInvoiceById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<InvoiceDTO>>> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(invoiceService.getAllInvoices(PageRequest.of(page, size))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceDTO>> createInvoice(@RequestBody CreateInvoiceDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Invoice created successfully", invoiceService.createInvoice(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(ApiResponse.success("Invoice deleted successfully", null));
    }
}