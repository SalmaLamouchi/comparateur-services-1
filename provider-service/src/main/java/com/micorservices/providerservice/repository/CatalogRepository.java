package com.micorservices.providerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micorservices.providerservice.model.Catalog;

    @Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {}

