package com.micorservices.providerservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micorservices.providerservice.model.Catalog;

// CatalogRepository.java

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    List<Catalog> findByUserId(Long userId);
    List<Catalog> findByCategoryId(Long categoryId);
}

