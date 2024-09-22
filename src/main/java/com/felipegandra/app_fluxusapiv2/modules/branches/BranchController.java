package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchIndexResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/branches")
public class BranchController {

    private final BranchService service;

    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BranchIndexResponse>> getIndex() {
        List<BranchIndexResponse> response = service.getBranchIndex();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> getById(@PathVariable String id) {
        BranchResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("contacts/{branchId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> getDetailsById(@PathVariable String branchId) {
        BranchResponse response =  service.getBranchDetailsById(branchId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchCreateRequest request) {
        BranchResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> update(@Valid @RequestBody BranchUpdateRequest request) {
        BranchResponse response = service.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
