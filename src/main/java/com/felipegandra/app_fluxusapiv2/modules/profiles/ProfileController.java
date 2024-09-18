package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("v2/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> getById() {
        var response = service.findById(1L);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileLogoResponse> getLogo() {
        var response = service.getLogoBase64();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/to-print")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileToPrintResponse> getToPrint() {
        var response = service.getToPrint();
        return ResponseEntity.ok(response);
    }

    @GetMapping("trading-name")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileTradingNameResponse> getTradingName() {
        var response = service.findTradingName();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> create(@Valid @RequestBody ProfileCreateRequest request) {
        var response = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/logo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> putLogo(@RequestBody ProfileUpdateLogoRequest request) {
        service.updateProfileLogo(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> update(@Valid @RequestBody ProfileUpdateRequest request) {
        var response = service.update(request);
        return ResponseEntity.ok(response);
    }

}