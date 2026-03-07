package com.micorservices.providerservice.model;

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
public class Provider {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 

    private Double serviceRadius; // En KM
    private Double latitude;
    private Double longitude;
    private boolean isActive = true; // Pour l'US2 : Suspendre/Réactiver
}

