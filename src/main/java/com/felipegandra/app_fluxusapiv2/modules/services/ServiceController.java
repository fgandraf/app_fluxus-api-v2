package com.felipegandra.app_fluxusapiv2.modules.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("v2/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService service) {
        this.serviceService = service;
    }

    @PostMapping
    public ResponseEntity<Service> create(@Valid @RequestBody Service service) {
        var createdService = serviceService.create(service);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdService.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Service>> getAll(Pageable pageable) {
        return ResponseEntity.ok(serviceService.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<Service> update(@RequestBody Service service) {
        return ResponseEntity.ok(serviceService.update(service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
