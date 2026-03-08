package com.microservices.userservice.service;


import com.microservices.userservice.dto.RoleDto;
import com.microservices.userservice.model.Role;
import com.microservices.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto create(RoleDto dto) {
        if (roleRepository.existsByName(dto.name()))
            throw new RuntimeException("Role already exists");

        Role saved = roleRepository.save(
            Role.builder()
                .name(dto.name())
                .description(dto.description())
                .build()
        );
        return toDto(saved);
    }

    @Override
    public RoleDto update(Long id, RoleDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.name());
        role.setDescription(dto.description());

        return toDto(roleRepository.save(role));
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id))
            throw new RuntimeException("Role not found");
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto getById(Long id) {
        return roleRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public RoleDto getByName(String name) {
        return roleRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    private RoleDto toDto(Role role) {
        return new RoleDto(role.getId(), role.getName(), role.getDescription());
    }
}