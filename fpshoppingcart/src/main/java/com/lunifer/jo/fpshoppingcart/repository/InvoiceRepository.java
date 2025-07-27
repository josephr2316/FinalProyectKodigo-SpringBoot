package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findByOrder_OrderId(Long orderId);
    
}
