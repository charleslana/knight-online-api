package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.model.dto.HeroBasicDTO;
import com.charles.knightonlineapi.model.dto.HeroDTO;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.service.HeroService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/hero")
@RequiredArgsConstructor
@Slf4j
public class HeroController {

    private final HeroService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create hero")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid HeroDTO dto) {
        log.info("REST request to create hero: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "update hero")
    @PutMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid HeroDTO dto) {
        log.info("REST request to update hero: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get hero")
    @GetMapping("/{id}")
    public ResponseEntity<HeroBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get hero: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all heroes")
    @GetMapping
    public ResponseEntity<Page<HeroBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST request to get all heroes");
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search hero name")
    @GetMapping("/search")
    public ResponseEntity<Page<HeroBasicDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search heroes");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete hero by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete hero by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}
