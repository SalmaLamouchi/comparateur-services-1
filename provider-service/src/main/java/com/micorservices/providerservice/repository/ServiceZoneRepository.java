package com.micorservices.providerservice.repository;


import com.micorservices.providerservice.model.ServiceZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceZoneRepository extends JpaRepository<ServiceZone, Long> {
    List<ServiceZone> findByUserId(Long userId);
    List<ServiceZone> findByCity(String city);

    // Trouver les prestataires dans un rayon donné (formule Haversine simplifiée)
    @Query("SELECT s FROM ServiceZone s WHERE " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(s.latitude)) * " +
           "cos(radians(s.longitude) - radians(:lng)) + " +
           "sin(radians(:lat)) * sin(radians(s.latitude)))) <= s.radiusKm")
    List<ServiceZone> findProvidersWithinRadius(
        @Param("lat") Double latitude,
        @Param("lng") Double longitude
    );
}