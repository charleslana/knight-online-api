package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.dto.UserHeroBasicDTO;
import com.charles.knightonlineapi.model.dto.UserHeroDTO;
import com.charles.knightonlineapi.service.UserHeroService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/hero")
@RequiredArgsConstructor
@Slf4j
public class UserHeroController {

    private final UserHeroService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create user hero")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserHeroDTO dto) {
        log.info("REST request to create user hero: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user hero")
    @GetMapping("/{id}")
    public ResponseEntity<UserHeroBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get user hero: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all user heroes")
    @GetMapping
    public ResponseEntity<Page<UserHeroBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST request to get all user heroes");
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete user hero by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete user hero by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}
