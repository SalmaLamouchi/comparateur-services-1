package com.micorservices.providerservice.dto;

import java.time.LocalDateTime;


public record AvailabilityDto(
    Long id,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    int maxSimultaneousServices,
    int currentBookingsCount,
    boolean isBooked,
    Long userId         
) {}