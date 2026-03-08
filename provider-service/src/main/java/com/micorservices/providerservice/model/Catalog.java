package com.micorservices.providerservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double price;
    private String imageUrl;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
