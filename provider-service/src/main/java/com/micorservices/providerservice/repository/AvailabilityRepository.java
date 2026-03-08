package com.micorservices.providerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micorservices.providerservice.model.Availability;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByUserId(Long userId);
    List<Availability> findByUserIdAndIsBookedFalse(Long userId);
}

