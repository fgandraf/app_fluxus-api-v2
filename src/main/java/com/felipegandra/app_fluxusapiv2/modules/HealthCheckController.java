package com.felipegandra.app_fluxusapiv2.modules;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getIndexByFolder() {
        return ResponseEntity.ok("Health Check: OK");
    }
}