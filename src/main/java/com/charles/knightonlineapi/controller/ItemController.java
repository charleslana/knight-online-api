package com.charles.knightonlineapi.controller;

import com.charles.knightonlineapi.model.dto.ItemBasicDTO;
import com.charles.knightonlineapi.model.dto.ItemDTO;
import com.charles.knightonlineapi.model.dto.ResponseDTO;
import com.charles.knightonlineapi.service.ItemService;
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
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create item")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid ItemDTO dto) {
        log.info("REST request to create item: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update item")
    @PutMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid ItemDTO dto) {
        log.info("REST request to update item: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get item")
    @GetMapping("/{id}")
    public ResponseEntity<ItemBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get item: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all items")
    @GetMapping
    public ResponseEntity<Page<ItemBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST request to get all items");
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search item name")
    @GetMapping("/search")
    public ResponseEntity<Page<ItemBasicDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search items");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete item by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST to delete item by id: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}
