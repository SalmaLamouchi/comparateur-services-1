package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserDtoRequest;
import com.microservices.userservice.dto.UserDtoResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDtoResponse createUser(UserDtoRequest userDtoRequest);
    List<UserDtoResponse> getAllUsers();
    Optional<UserDtoResponse> getUserById(Long id);
    Optional<UserDtoResponse> getUserByEmail(String email);
    Optional<UserDtoResponse> updateUser(Long id, UserDtoRequest userDtoRequest);
    boolean deleteUser(Long id);
    boolean emailExists(String email);
}
