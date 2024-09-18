package com.felipegandra.app_fluxusapiv2.modules.users;

import com.felipegandra.app_fluxusapiv2.modules.users.dtos.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody @Valid UserLoginRequest request) {
        var response = service.getUserToken(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        var response = service.findByEmail(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/professional/{professionalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getByProfessionalId(@PathVariable Long professionalId) {
        var response = service.findByProfessionalId(professionalId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        var response = service.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-info")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> updateInfo(@RequestBody @Valid UserUpdateInfoRequest request) {
        var response = service.updateInfo(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-config")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> updateConfig(@RequestBody @Valid UserUpdateConfigRequest request) {
        var response = service.updateConfig(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> activate(@PathVariable Long id) {
        var user = service.activate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> deactivate(@PathVariable Long id) {
        var user = service.deactivate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/upgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> upgradePermission(@PathVariable Long id) {
        var user = service.upgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/downgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserResponse> downgradePermission(@PathVariable Long id) {
        var user = service.downgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponse> getAll(Pageable page) {
        return service.getAll(page);
    }

}