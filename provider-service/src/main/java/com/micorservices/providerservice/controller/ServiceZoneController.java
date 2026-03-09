package com.micorservices.providerservice.controller;


import com.micorservices.providerservice.dto.ServiceZoneDto;
import com.micorservices.providerservice.service.ServiceZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/zones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceZoneController {

    private final ServiceZoneService serviceZoneService;

    @PostMapping
    public ResponseEntity<ServiceZoneDto> create(@RequestBody ServiceZoneDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceZoneService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceZoneDto> update(@PathVariable Long id, @RequestBody ServiceZoneDto dto) {
        return ResponseEntity.ok(serviceZoneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceZoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceZoneDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceZoneService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceZoneDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(serviceZoneService.getByUserId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceZoneDto>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(serviceZoneService.getByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ServiceZoneDto>> getNearby(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        return ResponseEntity.ok(serviceZoneService.findProvidersNearLocation(latitude, longitude));
    }
}