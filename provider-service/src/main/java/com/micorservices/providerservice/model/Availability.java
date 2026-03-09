package com.micorservices.providerservice.model;

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

    @Builder.Default                  
    private int currentBookingsCount = 0;

    @Builder.Default              
    private boolean isBooked = false;

    @Column(nullable = false)
    private Long userId;
}