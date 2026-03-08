package com.micorservices.providerservice.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micorservices.providerservice.model.Category;



public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
}