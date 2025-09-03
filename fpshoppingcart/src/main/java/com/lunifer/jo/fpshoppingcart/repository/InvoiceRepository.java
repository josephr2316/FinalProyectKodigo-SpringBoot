package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    @EntityGraph(attributePaths = {"order", "order.user"})
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    
    @EntityGraph(attributePaths = {"order", "order.user"})
    Optional<Invoice> findByOrder_OrderId(Long orderId);

    @Override
    @EntityGraph(attributePaths = {"order", "order.user"})
    List<Invoice> findAll();

    @EntityGraph(attributePaths = {"order", "order.user"})
    Page<Invoice> findAll(Pageable pageable);
}
