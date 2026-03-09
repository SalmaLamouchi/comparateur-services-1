package com.micorservices.providerservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_service_zones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String region;
    private String country;

    private Double latitude;
    private Double longitude;
    private Double radiusKm; // rayon de couverture en km

    @Column(nullable = false)
    private Long userId; // ID du prestataire dans UserService
}