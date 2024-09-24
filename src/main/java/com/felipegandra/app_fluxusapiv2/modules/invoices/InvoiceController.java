package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceDescriptionResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceUpdateRequest;
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
@RequestMapping("v2/invoices")
@Tag(name = "Invoice Controller", description = "Controladora responsável por gerenciar as faturas")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obter todas as faturas", description = "Retorna todas as faturas cadastradas.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        List<InvoiceResponse> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/description/{id}")
    @Operation(summary = "Obter a descrição de uma fatura por Id", description = "Retorna a descrição de uma fatura pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InvoiceDescriptionResponse> getDescription(@PathVariable Long id) {
         InvoiceDescriptionResponse response = service.getDescription(id);
         return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova fatura", description = "Insere uma nova fatura na base de dados.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceCreateRequest request) {
        InvoiceResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("totals")
    @Operation(summary = "Atualizar uma fatura", description = "Atualiza os subtotais e total de uma fatura existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InvoiceResponse> putTotals(@Valid @RequestBody InvoiceUpdateRequest request) {
        InvoiceResponse response = service.updateTotal(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma fatura por Id", description = "Exclui uma fatura pelo seu identificador único.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}