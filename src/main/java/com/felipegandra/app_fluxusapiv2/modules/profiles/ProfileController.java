package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.LogoViewModel;
import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.ProfileToPrintModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("v2/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Profile> getById() {
        return ResponseEntity.ok(service.findById(1L));
    }

    @GetMapping("/logo")
    public ResponseEntity<String> getLogo() {
        try {
            var logo = service.getLogoBase64();
            return ResponseEntity.ok(logo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/to-print")
    public ResponseEntity<ProfileToPrintModel> getToPrint() {
        ProfileToPrintModel profile = service.findToPrint();

        try {
            var logo = service.getLogoBase64();
            profile.setLogo(logo);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o logo: " + e.getMessage());
        }

        return ResponseEntity.ok(profile);
    }

    @GetMapping("trading-name")
    public ResponseEntity<String> getTradingName() {
        return ResponseEntity.ok(service.findTradingName());
    }

    @PostMapping
    public ResponseEntity<Profile> create(@Valid @RequestBody Profile profile) {
        profile.setId(1L);
        service.create(profile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("")
                .buildAndExpand(1L)
                .toUri();
        return ResponseEntity.created(location).body(profile);
    }

    @PutMapping("/logo")
    public ResponseEntity<Void> putLogo(@RequestBody LogoViewModel model) {
        try {
            service.setLogoBase64(model);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Profile> update(@RequestBody Profile profile) {
        profile.setId(1L);
        return ResponseEntity.ok(service.update(profile));
    }

}