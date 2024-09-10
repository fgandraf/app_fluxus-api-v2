package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.ProfessionalDetails;
import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.ProfessionalTagNameId;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/professionals")
public class ProfessionalController {

    private final ProfessionalService service;

    public ProfessionalController(ProfessionalService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<ProfessionalDetails>> getIndex() {
        return ResponseEntity.ok(service.getProfessionalIndex());
    }

    @GetMapping("tag-name-id")
    public ResponseEntity<List<ProfessionalTagNameId>> getTagNameIdIndex() {
        return ResponseEntity.ok(service.getTagNameId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professional> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
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