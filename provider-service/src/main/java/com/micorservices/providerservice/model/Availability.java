package com.micorservices.providerservice.model;
// Availability.java

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private int maxSimultaneousServices;

    @Builder.Default                  // ← nécessaire pour que la valeur par défaut fonctionne avec @Builder
    private int currentBookingsCount = 0;

    @Builder.Default                  // ← idem
    private boolean isBooked = false;

    @Column(nullable = false)
    private Long userId;
}