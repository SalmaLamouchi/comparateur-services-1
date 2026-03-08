package com.micorservices.providerservice.service;

// CatalogService.java

import java.util.List;

import com.micorservices.providerservice.dto.CatalogDto;

import java.util.List;

public interface CatalogService {
    CatalogDto create(CatalogDto dto);
    CatalogDto update(Long id, CatalogDto dto);
    void delete(Long id);
    CatalogDto getById(Long id);
    List<CatalogDto> getAll();
    List<CatalogDto> getByUserId(Long userId);
}