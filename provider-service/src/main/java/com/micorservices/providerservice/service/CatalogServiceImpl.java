package com.micorservices.providerservice.service;

import com.micorservices.providerservice.dto.CatalogDto;
import com.micorservices.providerservice.dto.CategoryDto;
import com.micorservices.providerservice.model.Catalog;
import com.micorservices.providerservice.model.Category; // ← import correct
import com.micorservices.providerservice.repository.CatalogRepository;
import com.micorservices.providerservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
// ↑ java.util.Locale.Category supprimé

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CatalogDto create(CatalogDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Catalog saved = catalogRepository.save(
            Catalog.builder()
                .title(dto.title())
                .description(dto.description())
                .price(dto.price())
                .imageUrl(dto.imageUrl())
                .userId(dto.userId())
                .category(category)
                .build()
        );
        return toDto(saved);
    }

    @Override
    public CatalogDto update(Long id, CatalogDto dto) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        catalog.setTitle(dto.title());
        catalog.setDescription(dto.description());
        catalog.setPrice(dto.price());
        catalog.setImageUrl(dto.imageUrl());
        catalog.setUserId(dto.userId());
        catalog.setCategory(category);

        return toDto(catalogRepository.save(catalog));
    }

    @Override
    public void delete(Long id) {
        if (!catalogRepository.existsById(id))
            throw new RuntimeException("Catalog not found");
        catalogRepository.deleteById(id);
    }

    @Override
    public CatalogDto getById(Long id) {
        return catalogRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));
    }

    @Override
    public List<CatalogDto> getAll() {
        return catalogRepository.findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogDto> getByUserId(Long userId) {
        return catalogRepository.findByUserId(userId)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    private CatalogDto toDto(Catalog c) {
        CategoryDto catDto = new CategoryDto(
            c.getCategory().getId(),
            c.getCategory().getName(),
            c.getCategory().getDescription()
        );
        return new CatalogDto(
            c.getId(),
            c.getTitle(),
            c.getDescription(),
            c.getImageUrl(),
            c.getPrice(),
            c.getUserId(),
            c.getCategory().getId(),
            catDto
        );
    }
}