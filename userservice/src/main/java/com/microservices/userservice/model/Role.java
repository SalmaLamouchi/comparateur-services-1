package com.microservices.userservice.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}