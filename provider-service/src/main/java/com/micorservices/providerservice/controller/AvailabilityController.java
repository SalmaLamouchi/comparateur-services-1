package com.micorservices.providerservice.controller;

import com.micorservices.providerservice.dto.AvailabilityDto;
import com.micorservices.providerservice.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider/availabilities")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<AvailabilityDto> create(@RequestBody AvailabilityDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityDto> update(@PathVariable Long id, @RequestBody AvailabilityDto dto) {
        return ResponseEntity.ok(availabilityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availabilityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(availabilityService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AvailabilityDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(availabilityService.getByUserId(userId));
    }

    @GetMapping("/user/{userId}/available")
    public ResponseEntity<List<AvailabilityDto>> getAvailableSlots(@PathVariable Long userId) {
        return ResponseEntity.ok(availabilityService.getAvailableSlotsByUserId(userId));
    }
}
