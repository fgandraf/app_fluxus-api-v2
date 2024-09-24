package com.felipegandra.app_fluxusapiv2.modules.users;

import com.felipegandra.app_fluxusapiv2.modules.users.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/users")
@Tag(name = "User Controller", description = "Controladora responsável por gerenciar os usuários")
public class UserController {

    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @PostMapping("login")
    @Operation(summary = "Obter token de um usuário", description = "Retorna o token de usuário válido.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest request) {
        UserTokenReponse response = service.getUserToken(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Obter usuário pelo nome-de-usuário", description = "Retorna um usuário pelo seu nome-de-usuário.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        UserResponse response = service.findByEmail(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/professional/{professionalId}")
    @Operation(summary = "Obter usuário vinculado à um profissional", description = "Retorna um usuário vinculado à um profissional pelo identificador único do profisisonal.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getByProfessionalId(@PathVariable Long professionalId) {
        UserResponse response = service.findByProfessionalId(professionalId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Criar um novo usuário", description = "Insere um novo usuário na base de dados.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        UserResponse response = service.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-info")
    @Operation(summary = "Atualizar informações de um usuário", description = "Atualiza as informações de um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> updateInfo(@RequestBody @Valid UserUpdateInfoRequest request) {
        UserResponse response = service.updateInfo(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-config")
    @Operation(summary = "Atualizar configurações de um usuário", description = "Atualiza as configurações de um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> updateConfig(@RequestBody @Valid UserUpdateConfigRequest request) {
        UserResponse response = service.updateConfig(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/activate/{id}")
    @Operation(summary = "Ativar um usuário", description = "Ativar um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> activate(@PathVariable Long id) {
        UserResponse user = service.activate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/deactivate/{id}")
    @Operation(summary = "Desativar um usuário", description = "Desativar um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> deactivate(@PathVariable Long id) {
        UserResponse user = service.deactivate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/upgrade-permission/{id}")
    @Operation(summary = "Promover as permissões de um usuário", description = "Promover as permissões de um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> upgradePermission(@PathVariable Long id) {
        UserResponse user = service.upgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/downgrade-permission/{id}")
    @Operation(summary = "Rebaixar as permissões de um usuário", description = "Rebaixar as permissões de um usuário existente.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> downgradePermission(@PathVariable Long id) {
        UserResponse user = service.downgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário por Id", description = "Retorna um usuário pelo seu identificador único.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obter todos os usuários", description = "Retorna todos os usuários cadastradas.")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponse> getAll(Pageable page) {
        return service.getAll(page);
    }

}