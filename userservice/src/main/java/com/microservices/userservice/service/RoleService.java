package com.microservices.userservice.service;

// com.microservices.userservice.service.RoleService

import com.microservices.userservice.dto.RoleDto;
import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Long id, RoleDto dto);
    void delete(Long id);
    RoleDto getById(Long id);
    RoleDto getByName(String name);
    List<RoleDto> getAll();
}