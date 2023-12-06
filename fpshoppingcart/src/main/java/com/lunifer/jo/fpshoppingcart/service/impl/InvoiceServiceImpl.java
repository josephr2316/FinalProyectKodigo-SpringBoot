package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.InvoiceDTO;
import com.lunifer.jo.fpshoppingcart.entity.Invoice;
import com.lunifer.jo.fpshoppingcart.entity.Order;
import com.lunifer.jo.fpshoppingcart.mapper.InvoiceMapper;
import com.lunifer.jo.fpshoppingcart.repository.InvoiceRepository;
import com.lunifer.jo.fpshoppingcart.repository.OrderRepository;
import com.lunifer.jo.fpshoppingcart.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.InvoiceDTOToInvoiceEntity(invoiceDTO);
        Optional<Order> optionalOrder = orderRepository.findById(invoiceDTO.getOrderId());

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            invoice.setOrder(order);
            invoiceRepository.save(invoice);
            InvoiceDTO invoiceDTO1 = invoiceMapper.invoiceEntityToInvoiceDTO(invoice);
            invoiceDTO1.setOrderId(invoice.getOrder().getOrderId());
            return invoiceDTO1;
        } else {
            // Manejar el caso en que no se encuentre la orden
            throw new EntityNotFoundException("Cannot find order with ID: " + invoiceDTO.getOrderId());
        }

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
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Invoice #: " + invoiceId));


        existingInvoice.setTotalAmount(invoiceDTO.getTotalAmount());

        Invoice updatedInvoiceEntity = invoiceRepository.save(existingInvoice);

        return invoiceMapper.invoiceEntityToInvoiceDTO(updatedInvoiceEntity);
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        if (invoiceRepository.existsById(invoiceId)) {
            invoiceRepository.deleteById(invoiceId);
        } else {

            throw new EntityNotFoundException("Cannot find Invoice #: " + invoiceId);
        }

    }
}
