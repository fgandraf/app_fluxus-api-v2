package com.felipegandra.app_fluxusapiv2.modules.profiles;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v2/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Profile> create(@Valid @RequestBody Profile profile) {
        var createdProfile = service.create(profile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProfile.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Profile> update(@RequestBody Profile profile) {
        return ResponseEntity.ok(service.update(profile));
    }

}
