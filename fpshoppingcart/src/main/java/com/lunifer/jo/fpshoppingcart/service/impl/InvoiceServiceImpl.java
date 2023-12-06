package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.dto.OrderDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.mapper.OrderMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import com.lunifer.jo.fpshoppingcart.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final OrderMapper  orderMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, OrderMapper orderMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.orderMapper = orderMapper;
    }


    @Override

    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.InvoiceDTOToInvoiceEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.invoiceEntityToInvoiceDTO(invoice);
    }

    @Override
    @Transactional
    public InvoiceDTO getInvoiceByOrderId(Long orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId);

        if (invoice != null) {
            return invoiceMapper.invoiceEntityToInvoiceDTO(invoice);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public InvoiceDTO updateInvoice(InvoiceDTO invoiceDTO, Long invoiceId) {

        Invoice existingInvoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la factura #: " + invoiceId));


        existingInvoice.setTotalAmount(invoiceDTO.getTotalAmount());

        Invoice updatedInvoiceEntity = invoiceRepository.save(existingInvoice);

        return invoiceMapper.invoiceEntityToInvoiceDTO(updatedInvoiceEntity);
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        if (invoiceRepository.existsById(invoiceId)) {
            invoiceRepository.deleteById(invoiceId);
        } else {

            throw new EntityNotFoundException("No se encontró la factura #: " + invoiceId);
        }

    }

    @Override
    @Transactional
    public void deleteInvoiceByOrderId(long orderId) {
        invoiceRepository.deleteInvoiceByOrderOrderId(orderId);
    }

    @Override
    public void deleteInvoicesByOrders(List<OrderDTO> orders) {
        List<Order> orderList = new ArrayList<>();
        orderList = orders.stream().map(orderMapper::orderDTOToOrderEntity).toList();
        List<Invoice> invoicesToDelete = invoiceRepository.findInvoicesByOrders(orderList);
        invoiceRepository.deleteAll(invoicesToDelete);
    }

}

