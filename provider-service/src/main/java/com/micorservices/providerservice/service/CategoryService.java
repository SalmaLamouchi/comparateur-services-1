
    
package com.micorservices.providerservice.service;

import com.micorservices.providerservice.dto.CategoryDto;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto create(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    void delete(Long id);
    CategoryDto getById(Long id);
    List<CategoryDto> getAll();
    CategoryDto getByName(String name);
}