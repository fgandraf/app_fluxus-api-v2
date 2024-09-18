package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("v2/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/flow")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderFlowResponse>> getFlow() {
        var response = service.getOrdersFlow();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> getCities() {
        var response = service.getDistinctsCities();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/done-to-invoice")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDoneToInvoiceResponse>> getDoneToInvoice() {
        var response = service.getOrdersDoneToInvoice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filtered/{filter}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderFilteredResponse>> getFiltered(@PathVariable String filter) {
        var response = service.getOrdersFiltered(filter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoiced/{invoiceId}")
    public ResponseEntity<List<OrderInvoicedResponse>> getInvoiced(@PathVariable Long invoiceId) {
        var response = service.getOrdersInvoiced(invoiceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/professionals/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalNameIdResponse>> getProfessionalsByInvoiceId(@PathVariable Long invoiceId) {
        var response = service.getOrderProfessionalByInvoice(invoiceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest order) {
        var response = service.create(order);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> update(@RequestBody OrderUpdateRequest order) {
        var response = service.update(order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update-invoice/{invoiceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateToInvoice(@PathVariable Long invoiceId, @RequestBody List<Long> orders) {
        service.updateToInvoice(invoiceId, orders);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-status/{orderId},{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateStatus(@PathVariable Long orderId, @PathVariable int status) {
        service.updateStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}