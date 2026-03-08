package com.micorservices.providerservice.service;

    

import com.micorservices.providerservice.dto.CategoryDto;
import com.micorservices.providerservice.model.Category; 
import com.micorservices.providerservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryDto dto) {
        if (categoryRepository.existsByName(dto.name()))
            throw new RuntimeException("Category already exists");

        Category saved = categoryRepository.save(
            Category.builder()
                .name(dto.name())
                .description(dto.description())
                .build()
        );
        return toDto(saved);
    }

    @Override
    public CategoryDto getByName(String name) {
    return categoryRepository.findByName(name)
            .map(this::toDto)
            .orElseThrow(() -> new RuntimeException("Category not found"));
}

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(dto.name());
        category.setDescription(dto.description());

        return toDto(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id))
            throw new RuntimeException("Category not found");
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    private CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName(), c.getDescription());
    }
}