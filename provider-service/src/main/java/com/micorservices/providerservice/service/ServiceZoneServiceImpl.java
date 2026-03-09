package com.micorservices.providerservice.service;


import com.micorservices.providerservice.dto.ServiceZoneDto;
import com.micorservices.providerservice.model.ServiceZone;
import com.micorservices.providerservice.repository.ServiceZoneRepository;
import com.micorservices.providerservice.service.ServiceZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceZoneServiceImpl implements ServiceZoneService {

    private final ServiceZoneRepository serviceZoneRepository;

    @Override
    public ServiceZoneDto create(ServiceZoneDto dto) {
        ServiceZone saved = serviceZoneRepository.save(
            ServiceZone.builder()
                .city(dto.city())
                .region(dto.region())
                .country(dto.country())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .radiusKm(dto.radiusKm())
                .userId(dto.userId())
                .build()
        );
        return toDto(saved);
    }

    @Override
    public ServiceZoneDto update(Long id, ServiceZoneDto dto) {
        ServiceZone zone = serviceZoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceZone not found"));

        zone.setCity(dto.city());
        zone.setRegion(dto.region());
        zone.setCountry(dto.country());
        zone.setLatitude(dto.latitude());
        zone.setLongitude(dto.longitude());
        zone.setRadiusKm(dto.radiusKm());
        zone.setUserId(dto.userId());

        return toDto(serviceZoneRepository.save(zone));
    }

    @Override
    public void delete(Long id) {
        if (!serviceZoneRepository.existsById(id))
            throw new RuntimeException("ServiceZone not found");
        serviceZoneRepository.deleteById(id);
    }

    @Override
    public ServiceZoneDto getById(Long id) {
        return serviceZoneRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("ServiceZone not found"));
    }

    @Override
    public List<ServiceZoneDto> getByUserId(Long userId) {
        return serviceZoneRepository.findByUserId(userId)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceZoneDto> getByCity(String city) {
        return serviceZoneRepository.findByCity(city)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceZoneDto> findProvidersNearLocation(Double latitude, Double longitude) {
        return serviceZoneRepository.findProvidersWithinRadius(latitude, longitude)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    private ServiceZoneDto toDto(ServiceZone s) {
        return new ServiceZoneDto(
            s.getId(),
            s.getCity(),
            s.getRegion(),
            s.getCountry(),
            s.getLatitude(),
            s.getLongitude(),
            s.getRadiusKm(),
            s.getUserId()
        );
    }
}