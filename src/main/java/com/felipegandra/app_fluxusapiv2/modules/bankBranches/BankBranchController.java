package com.felipegandra.app_fluxusapiv2.modules.bankBranches;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("v2/bank-branches")
public class BankBranchController {

    private final BankBranchService service;

    public BankBranchController(BankBranchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BankBranch> create(@Valid @RequestBody BankBranch bankBranch) {
        BankBranch createdBankBranch = service.create(bankBranch);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBankBranch.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBankBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankBranch> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<BankBranch>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<BankBranch> update(@RequestBody BankBranch bankBranch) {
        return ResponseEntity.ok(service.update(bankBranch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
