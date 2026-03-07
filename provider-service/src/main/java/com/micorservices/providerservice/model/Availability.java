package com.micorservices.providerservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Availability {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    
    private int maxSimultaneousServices; // Nombre max de clients en même temps
    private int currentBookingsCount = 0; // Compteur actuel
    
    private boolean isBooked = false; // Devient true si currentBookingsCount == maxSimultaneousServices

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
}