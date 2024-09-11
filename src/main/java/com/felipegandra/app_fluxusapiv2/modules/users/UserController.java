package com.felipegandra.app_fluxusapiv2.modules.users;

import com.felipegandra.app_fluxusapiv2.config.TokenService;
import com.felipegandra.app_fluxusapiv2.modules.users.dtos.*;
import com.felipegandra.app_fluxusapiv2.modules.users.enums.UserRole;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/users")
public class UserController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserController(UserService service, AuthenticationManager authenticationManager, TokenService tokenService){
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginInput loginInput) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginInput.email(),
                loginInput.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var user = (User)auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginOutput(token));
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput getByUsername(@PathVariable String username) {
        return new UserOutput(service.findByEmail(username));
    }

    @GetMapping("/professional/{professionalId}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput getByProfessionalId(@PathVariable Long professionalId) {
        return new UserOutput(service.findById(professionalId));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput register(@RequestBody @Valid UserRegisterInput userRegisterInput) {
        var user = new User();
        user.setTechnicianResponsible(false);
        user.setLegalResponsible(false);
        user.setActive(false);
        user.setEmail(userRegisterInput.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegisterInput.password()));
        user.setRole(UserRole.USER);

        return service.save(user);
    }

    @PutMapping("/update-info")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserOutput updateInfo(@RequestBody @Valid UserInfoUpdateInput userInfoUpdateInput) {
        var user = service.findById(userInfoUpdateInput.id());
        user.setEmail(userInfoUpdateInput.email());
        user.setPassword(userInfoUpdateInput.userPassword());

        return service.update(user);
    }

    @PutMapping("/update-config")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserOutput updateConfig(@RequestBody @Valid UserConfigUpdateInput userConfigUpdateInput) {
        var user = service.findById(userConfigUpdateInput.id());
        user.setProfessionalId(userConfigUpdateInput.professionalId());
        user.setLegalResponsible(userConfigUpdateInput.legalResponsible());
        user.setTechnicianResponsible(userConfigUpdateInput.technicianResponsible());

        return service.update(user);
    }

    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity activate(@PathVariable Long id) {
        var user = service.activate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity deactivate(@PathVariable Long id) {
        var user = service.deactivate(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/upgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity upgradePermission(@PathVariable Long id) {
        var user = service.upgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/downgrade-permission/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity downgradePermission(@PathVariable Long id) {
        var user = service.downgradePermission(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput getById(@PathVariable Long id) {
        return new UserOutput(service.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserOutput> getAll(Pageable page) {
        return service.getAll(page);
    }

}