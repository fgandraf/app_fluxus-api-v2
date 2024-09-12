package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v2/branches")
public class BranchController {

    private final BranchService service;

    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BranchDetails>> getIndex() {
        return ResponseEntity.ok(service.getBranchIndex());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @GetMapping("contacts/{branchId}")
    public Optional<BranchDetails> getDetailsById(@PathVariable String branchId) {
        return service.getBranchDetailsById(branchId);
    }

    @PostMapping
    public ResponseEntity<Branch> create(@Valid @RequestBody Branch branch) {
        Branch createdBranch = service.create(branch);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBranch.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBranch);
    }

    @PutMapping
    public ResponseEntity<Branch> update(@RequestBody Branch branch) {
        return ResponseEntity.ok(service.update(branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
