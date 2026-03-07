package com.micorservices.providerservice.dto;

    public record ProviderDto(
    Long id, String firstName, String lastName, String email,
    CategoryDto category, Double serviceRadius, Double latitude, 
    Double longitude, boolean isActive
) {}
