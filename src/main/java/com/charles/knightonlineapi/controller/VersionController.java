package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@Slf4j
public class VersionController {

    @Operation(summary = "Get backend version")
    @GetMapping
    public ResponseEntity<String> get() {
        log.info("REST to get backend version");
        return ResponseEntity.ok(VersionService.VERSION);
    }
}
