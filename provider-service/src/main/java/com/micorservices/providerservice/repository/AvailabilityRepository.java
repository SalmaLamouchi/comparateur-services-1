package com.micorservices.providerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micorservices.providerservice.model.Availability;

    @Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {}

