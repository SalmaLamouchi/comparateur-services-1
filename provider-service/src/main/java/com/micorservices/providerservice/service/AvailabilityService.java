package com.micorservices.providerservice.service;
import com.micorservices.providerservice.dto.AvailabilityDto;
import java.util.List;

public interface AvailabilityService {
    AvailabilityDto create(AvailabilityDto dto);
    AvailabilityDto update(Long id, AvailabilityDto dto);
    void delete(Long id);
    AvailabilityDto getById(Long id);
    List<AvailabilityDto> getByUserId(Long userId);
    List<AvailabilityDto> getAvailableSlotsByUserId(Long userId);
}