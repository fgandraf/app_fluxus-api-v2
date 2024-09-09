package com.felipegandra.app_fluxusapiv2.modules.professionals;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("v2/professionals")
public class ProfessionalController {

    private final ProfessionalService service;

    public ProfessionalController(ProfessionalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Professional> create(@Valid @RequestBody Professional professional) {
        var createdProfessional = service.create(professional);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProfessional.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProfessional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professional> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Professional>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<Professional> update(@RequestBody Professional professional) {
        return ResponseEntity.ok(service.update(professional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}