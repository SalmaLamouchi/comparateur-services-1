package com.micorservices.providerservice.dto;



public record CatalogDto(
    Long id,
    String title,
    String description,
    String imageUrl,
    Double price,
    Long userId,
    Long categoryId,
    CategoryDto category
) {}