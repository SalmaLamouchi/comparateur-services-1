package com.micorservices.providerservice.dto;

import java.util.List;

public record CatalogDto(
    Long id,
    String title,
    String description,
    String imageUrl,
    Double price,
    Long userId,
    List<Long> categoryIds,
    List<CategoryDto> categories
) {}