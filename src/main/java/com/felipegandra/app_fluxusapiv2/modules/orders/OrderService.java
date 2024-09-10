package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
    }

    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Order create(Order order) {
        return repository.save(order);
    }

    public Order update(Order order){
        return repository
                .findById(order.getId())
                .map(foundOrder -> {
                    updateEntity(foundOrder, order);
                    return repository.save(foundOrder);
                })
                .orElseThrow(() ->
                        new NotFoundException("Order", order.getId())
                );
    }

    public void delete(Long id) {
        var branch = repository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
        repository.delete(branch);
    }

    private void updateEntity(Order foundOrder, Order updatedOrder) {
        foundOrder.setReferenceCode(updatedOrder.getReferenceCode());
        foundOrder.setBranch(updatedOrder.getBranch());
        foundOrder.setOrderDate(updatedOrder.getOrderDate());
        foundOrder.setDeadline(updatedOrder.getDeadline());
        foundOrder.setProfessional(updatedOrder.getProfessional());
        foundOrder.setService(updatedOrder.getService());
        foundOrder.setServiceAmount(updatedOrder.getServiceAmount());
        foundOrder.setMileageAllowance(updatedOrder.getMileageAllowance());
        foundOrder.setSiopi(updatedOrder.getSiopi());
        foundOrder.setCustomerName(updatedOrder.getCustomerName());
        foundOrder.setCity(updatedOrder.getCity());
        foundOrder.setContactName(updatedOrder.getContactName());
        foundOrder.setContactPhone(updatedOrder.getContactPhone());
        foundOrder.setCoordinates(updatedOrder.getCoordinates());
        foundOrder.setStatus(updatedOrder.getStatus());
        foundOrder.setPendingDate(updatedOrder.getPendingDate());
        foundOrder.setSurveyDate(updatedOrder.getSurveyDate());
        foundOrder.setDoneDate(updatedOrder.getDoneDate());
        foundOrder.setInvoiced(updatedOrder.getInvoiced());
        foundOrder.setInvoice(updatedOrder.getInvoice());
    }
}
