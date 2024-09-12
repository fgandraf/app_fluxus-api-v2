package com.felipegandra.app_fluxusapiv2.modules.invoices;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v2/invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/description/{id}")
    public ResponseEntity<Optional<String>> getDescription(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDescription(id));
    }

    @PostMapping
    public ResponseEntity<Invoice> create(@Valid @RequestBody Invoice invoice) {
        var createdInvoice = service.create(invoice);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdInvoice.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdInvoice);
    }

    @PutMapping("totals")
    public ResponseEntity<Boolean> putTotals(@RequestBody Invoice invoice) {
        if (service.updateTotal(invoice) == 0)
            return ResponseEntity.ok(false);

        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}