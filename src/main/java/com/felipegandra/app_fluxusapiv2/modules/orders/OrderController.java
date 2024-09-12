package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/flow")
    public ResponseEntity<List<OrderFlowOutput>> getFlow() {
        return ResponseEntity.ok(service.findFlow());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(service.findCities());
    }

    @GetMapping("/done-to-invoice")
    public ResponseEntity<List<OrderDoneToInvoice>> getDoneToInvoice() {
        return ResponseEntity.ok(service.findDoneToInvoice());
    }

    @GetMapping("/filtered/{filter}")
    public ResponseEntity<List<OrderFiltered>> getFiltered(@PathVariable String filter) {
        return ResponseEntity.ok(service.findFiltered(filter));
    }

    @GetMapping("/invoiced/{invoiceId}")
    public ResponseEntity<List<OrderInvoiced>> getInvoiced(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(service.findInvoiced(invoiceId));
    }

    @GetMapping("/professionals/{invoiceId}")
    public ResponseEntity<List<ProfessionalNameId>> getProfessionalsByInvoiceId(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(service.findProfessional(invoiceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderCreateInput order) {
        var createdOrder = service.create(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdOrder);
    }

    @PutMapping
    public ResponseEntity<Order> update(@RequestBody OrderUpdateInput order) {
        return ResponseEntity.ok(service.update(order));
    }

    @PutMapping("update-invoice/{invoiceId}")
    public ResponseEntity updateToInvoice(@PathVariable Long invoiceId, @RequestBody List<Long> orders) {
        service.updateToInvoice(invoiceId, orders);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("update-status/{orderId},{status}")
    public ResponseEntity updateStatus(@PathVariable Long orderId, @PathVariable int status) {
        service.updateStatus(orderId, status);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}