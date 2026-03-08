
    
package com.micorservices.providerservice.service;

import com.micorservices.providerservice.dto.CategoryDto;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDTO);
    CategoryDto update(Long id, CategoryDto categoryDTO); 
    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    Optional<CategoryDto> findByName(String name); 
    void delete(Long id);
}
