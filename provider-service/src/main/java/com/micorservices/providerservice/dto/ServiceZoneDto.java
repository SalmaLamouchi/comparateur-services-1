package com.micorservices.providerservice.dto;

public record ServiceZoneDto(
    Long id,
    String city,
    String region,
    String country,
    Double latitude,
    Double longitude,
    Double radiusKm,
    Long userId
) {}