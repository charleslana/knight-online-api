package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.model.dto.UserItemBasicDTO;
import com.charles.knightonlineapi.model.dto.UserItemDTO;
import com.charles.knightonlineapi.service.UserItemService;
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
@RequestMapping("/user/item")
@RequiredArgsConstructor
@Slf4j
public class UserItemController {

    private final UserItemService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create user item")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserItemDTO dto) {
        log.info("REST request to create user item: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user item")
    @GetMapping("/{id}")
    public ResponseEntity<UserItemBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get user item: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all user items")
    @GetMapping
    public ResponseEntity<Page<UserItemBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST request to get all user heroes");
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete user item by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete user hero by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}
