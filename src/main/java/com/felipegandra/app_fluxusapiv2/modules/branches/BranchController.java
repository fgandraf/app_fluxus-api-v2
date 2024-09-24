package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchIndexResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/branches")
@Tag(name = "Branch Controller", description = "Controladora responsável por gerenciar as agências")
public class BranchController {

    private final BranchService service;

    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obter todas as agências", description = "Retorna todas as agências cadastradas.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BranchIndexResponse>> getIndex() {
        List<BranchIndexResponse> response = service.getBranchIndex();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter agência por Id", description = "Retorna uma agência pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> getById(@PathVariable String id) {
        BranchResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("contacts/{branchId}")
    @Operation(summary = "Obter contatos da agência por Id", description = "Retorna as informações de contato de uma agência pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> getDetailsById(@PathVariable String branchId) {
        BranchResponse response =  service.getBranchDetailsById(branchId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova agência", description = "Insere uma nova agência na base de dados.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchCreateRequest request) {
        BranchResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @Operation(summary = "Atualizar uma agência", description = "Atualiza as informações de uma agência existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BranchResponse> update(@Valid @RequestBody BranchUpdateRequest request) {
        BranchResponse response = service.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma agência por Id", description = "Exclui uma agência pelo seu identificador único.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
