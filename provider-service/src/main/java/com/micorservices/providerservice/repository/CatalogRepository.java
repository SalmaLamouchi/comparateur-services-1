package com.micorservices.providerservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.micorservices.providerservice.model.Catalog;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    List<Catalog> findByUserId(Long userId);
    List<Catalog> findByCategoryId(Long categoryId);
}