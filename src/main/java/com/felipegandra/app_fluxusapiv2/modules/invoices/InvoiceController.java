package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceDescriptionResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("v2/invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        var response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/description/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InvoiceDescriptionResponse> getDescription(@PathVariable Long id) {
         var response = service.getDescription(id);
         return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceCreateRequest request) {
        var response = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("totals")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InvoiceResponse> putTotals(@Valid @RequestBody InvoiceUpdateRequest request) {
        var response = service.updateTotal(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}