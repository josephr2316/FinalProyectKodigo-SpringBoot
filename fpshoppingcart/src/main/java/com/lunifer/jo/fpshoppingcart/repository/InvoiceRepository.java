package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT i FROM Invoice i WHERE i.order.orderId = :orderId")
    Invoice findByOrderId(Long orderId);
    void deleteInvoiceByOrderOrderId(long orderId);
}
