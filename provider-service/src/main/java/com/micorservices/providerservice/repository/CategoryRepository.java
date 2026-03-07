package com.micorservices.providerservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micorservices.providerservice.model.Category;

    @Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}

