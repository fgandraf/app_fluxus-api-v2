package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/professionals")
@Tag(name = "Professional Controller", description = "Controladora responsável por gerenciar os profissionais")
public class ProfessionalController {

    private final ProfessionalService service;

    public ProfessionalController(ProfessionalService service) {
        this.service = service;
    }


    @GetMapping
    @Operation(summary = "Obter o índice de todos os profissionais", description = "Retorna o índice de todos os profissionais cadastrados.", security = @SecurityRequirement(name = "bearerAuth"))
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalIndexResponse>> getIndex() {
        List<ProfessionalIndexResponse> response = service.getProfessionalIndex();
        return ResponseEntity.ok(response);
    }

    @GetMapping("tag-name-id")
    @Operation(summary = "Obter o tag de todos os profissionais", description = "Retorna o tag de todos os profissionais cadastrados.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalTagNameIdResponse>> getTagNameIdIndex() {
        List<ProfessionalTagNameIdResponse> response = service.getTagNameId();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter profissional por Id", description = "Retorna um profissional pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        ProfessionalResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar um novo profissional", description = "Insere um novo profissional na base de dados.")
    public ResponseEntity<ProfessionalResponse> create(@Valid @RequestBody ProfessionalCreateRequest request) {
        ProfessionalResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @Operation(summary = "Atualizar um profissional", description = "Atualiza as informações de um profissional existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfessionalResponse> update(@Valid @RequestBody ProfessionalUpdateRequest request) {
        ProfessionalResponse response = service.update(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um profissional por Id", description = "Exclui um profissional pelo seu identificador único.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}