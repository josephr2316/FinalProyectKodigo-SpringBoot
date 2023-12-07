package com.lunifer.jo.fpshoppingcart.repository;

import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    @Query("SELECT i FROM Invoice i WHERE i.order.orderId = :orderId")
    Invoice findByOrderId(Long orderId);
    void deleteInvoiceByOrderOrderId(long orderId);
    @Query("SELECT i FROM Invoice i WHERE i.order IN :orders")
    List<Invoice> findInvoicesByOrders(@Param("orders") List<Order> orders);
}
