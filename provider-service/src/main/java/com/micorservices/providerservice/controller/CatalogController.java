package com.micorservices.providerservice.controller;

import com.micorservices.providerservice.dto.CatalogDto;
import com.micorservices.providerservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/catalogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping
    public ResponseEntity<CatalogDto> create(@RequestBody CatalogDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogDto> update(@PathVariable Long id, @RequestBody CatalogDto dto) {
        return ResponseEntity.ok(catalogService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catalogService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CatalogDto>> getAll() {
        return ResponseEntity.ok(catalogService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CatalogDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(catalogService.getByUserId(userId));
    }
}