package com.microservices.userservice.service;

import lombok.RequiredArgsConstructor;
import com.microservices.userservice.dto.UserDtoRequest;
import com.microservices.userservice.dto.UserDtoResponse;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;

import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDtoResponse createUser(UserDtoRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) 
                .image(request.getImage())
                .roles(request.getRoles())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public List<UserDtoResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Optional<UserDtoResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public Optional<UserDtoResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToResponse);
    }

    @Override
    public Optional<UserDtoResponse> updateUser(Long id, UserDtoRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(request.getUsername());
                    user.setEmail(request.getEmail());
                    user.setImage(request.getImage());
                    user.setRoles(request.getRoles());
                    User savedUser = userRepository.save(user);
                    return mapToResponse(savedUser);
                });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserDtoResponse mapToResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .image(user.getImage())
                .roles(user.getRoles())
                .isActive(user.isActive())
                .build();
    }
}
