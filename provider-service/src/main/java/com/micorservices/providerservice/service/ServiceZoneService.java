package com.micorservices.providerservice.service;

import com.micorservices.providerservice.dto.ServiceZoneDto;
import java.util.List;

public interface ServiceZoneService {
    ServiceZoneDto create(ServiceZoneDto dto);
    ServiceZoneDto update(Long id, ServiceZoneDto dto);
    void delete(Long id);
    ServiceZoneDto getById(Long id);
    List<ServiceZoneDto> getByUserId(Long userId);
    List<ServiceZoneDto> getByCity(String city);
    List<ServiceZoneDto> findProvidersNearLocation(Double latitude, Double longitude);
}