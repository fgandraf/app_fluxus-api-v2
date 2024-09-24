package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("v2/profiles")
@Tag(name = "Profile Controller", description = "Controladora responsável por gerenciar o perfil da empresa")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obter perfil", description = "Retorna os dados do perfil.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> getById() {
        ProfileResponse response = service.findById(1L);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logo")
    @Operation(summary = "Obter logotipo", description = "Retorna o logotipo do perfil.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileLogoResponse> getLogo() {
        ProfileLogoResponse response = service.getLogoBase64();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/to-print")
    @Operation(summary = "Obter dados para impressão do perfil", description = "Retorna os dados para impressão do perfil.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileToPrintResponse> getToPrint() {
        ProfileToPrintResponse response = service.getToPrint();
        return ResponseEntity.ok(response);
    }

    @GetMapping("trading-name")
    @Operation(summary = "Obter o nome da fantasia", description = "Retorna o nome fantasia do perfil.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileTradingNameResponse> getTradingName() {
        ProfileTradingNameResponse response = service.findTradingName();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar um novo perfil", description = "Insere um novo perfil na base de dados.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> create(@Valid @RequestBody ProfileCreateRequest request) {
        ProfileResponse response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/logo")
    @Operation(summary = "Atualiza o logotipo", description = "Atualiza o logotipo do perfil existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> putLogo(@RequestBody ProfileUpdateLogoRequest request) {
        service.updateProfileLogo(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualiza o perfil", description = "Atualiza as informações do perfil existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> update(@Valid @RequestBody ProfileUpdateRequest request) {
        ProfileResponse response = service.update(request);
        return ResponseEntity.ok(response);
    }

}