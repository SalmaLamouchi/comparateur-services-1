package com.microservices.userservice.service;


import com.microservices.userservice.dto.*;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.security.JwtUtil;
import com.microservices.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDto login(LoginDto dto) {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive())
            throw new RuntimeException("Account is suspended");

        if (!passwordEncoder.matches(dto.password(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponseDto(token, user.getUsername(), user.getRoles());
    }

@Override
public UserDtoResponse register(UserDtoRequest dto) {
    if (userRepository.existsByUsername(dto.getUsername()))  
        throw new RuntimeException("Username already exists");
    if (userRepository.existsByEmail(dto.getEmail()))        
        throw new RuntimeException("Email already exists");

    User saved = userRepository.save(
        User.builder()
            .username(dto.getUsername())    
            .email(dto.getEmail())          
            .password(passwordEncoder.encode(dto.getPassword())) 
            .image(dto.getImage())        
            .roles(dto.getRoles())          
            .isActive(true)
            .build()
    );

    return new UserDtoResponse(
        saved.getId(),
        saved.getUsername(),
        saved.getEmail(),
        saved.getImage(),
        saved.getRoles(),
        saved.isActive()
    );
}
    @Override
    public void changePassword(ChangePasswordDto dto) {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword()))
            throw new RuntimeException("Old password incorrect");

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }
}