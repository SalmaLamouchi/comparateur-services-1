package com.micorservices.providerservice.service;

    

import com.micorservices.providerservice.dto.CategoryDto;
import com.micorservices.providerservice.model.Category; 
import com.micorservices.providerservice.repository.CategoryRepository;
import com.micorservices.providerservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional 
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        
        Category saved = categoryRepository.save(category);
        return mapToDto(saved);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable avec l'id : " + id));
        
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        
        return mapToDto(categoryRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDto> findByName(String name) {
       
        return categoryRepository.findByName(name)
                .map(this::mapToDto);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}

