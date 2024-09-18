package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("v2/professionals")
public class ProfessionalController {

    private final ProfessionalService service;

    public ProfessionalController(ProfessionalService service) {
        this.service = service;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalIndexResponse>> getIndex() {
        var response = service.getProfessionalIndex();
        return ResponseEntity.ok(response);
    }

    @GetMapping("tag-name-id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalTagNameIdResponse>> getTagNameIdIndex() {
        var response = service.getTagNameId();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfessionalResponse> create(@Valid @RequestBody ProfessionalCreateRequest request) {
        var response = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfessionalResponse> update(@Valid @RequestBody ProfessionalUpdateRequest request) {
        var response = service.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}