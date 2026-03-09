package com.micorservices.providerservice.service;

import com.micorservices.providerservice.dto.CatalogDto;
import com.micorservices.providerservice.dto.CategoryDto;
import com.micorservices.providerservice.model.Catalog;
import com.micorservices.providerservice.model.Category; // ← import correct
import com.micorservices.providerservice.repository.CatalogRepository;
import com.micorservices.providerservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CatalogDto create(CatalogDto dto) {
        Set<Category> categories = new HashSet<>();
        for (Long categoryId : dto.categoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            categories.add(category);
        }

        Catalog saved = catalogRepository.save(
            Catalog.builder()
                .title(dto.title())
                .description(dto.description())
                .price(dto.price())
                .imageUrl(dto.imageUrl())
                .userId(dto.userId())
                .categories(categories)
                .build()
        );
        return toDto(saved);
    }

    @Override
    public CatalogDto update(Long id, CatalogDto dto) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        Set<Category> categories = new HashSet<>();
        for (Long categoryId : dto.categoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            categories.add(category);
        }

        catalog.setTitle(dto.title());
        catalog.setDescription(dto.description());
        catalog.setPrice(dto.price());
        catalog.setImageUrl(dto.imageUrl());
        catalog.setUserId(dto.userId());
        catalog.setCategories(categories);

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
        List<CategoryDto> catDtos = c.getCategories().stream()
                .map(cat -> new CategoryDto(cat.getId(), cat.getName(), cat.getDescription()))
                .collect(Collectors.toList());
        List<Long> categoryIds = c.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        return new CatalogDto(
            c.getId(),
            c.getTitle(),
            c.getDescription(),
            c.getImageUrl(),
            c.getPrice(),
            c.getUserId(),
            categoryIds,
            catDtos
        );
    }
}