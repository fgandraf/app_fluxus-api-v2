package com.felipegandra.app_fluxusapiv2.modules.services;

import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceResponse;
import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceUpdateRequest;
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
@RequestMapping("v2/services")
@Tag(name = "Service Controller", description = "Controladora responsável por gerenciar os serviços")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService service) {
        this.serviceService = service;
    }

    @GetMapping
    @Operation(summary = "Obter todos os serviços", description = "Retorna todos os serviços cadastrados.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ServiceResponse>> getAll() {
        List<ServiceResponse> response = serviceService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter serviço por Id", description = "Retorna um serviço pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        ServiceResponse response = serviceService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar um novo serviço", description = "Insere um novo serviço na base de dados.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody ServiceCreateRequest request) {
        ServiceResponse response = serviceService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @Operation(summary = "Atualizar um serviço", description = "Atualiza as informações de um serviço existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServiceResponse> update(@Valid @RequestBody ServiceUpdateRequest request) {
        ServiceResponse response = serviceService.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um serviço por Id", description = "Exclui um serviço pelo seu identificador único.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}