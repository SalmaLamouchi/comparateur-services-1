package com.microservices.userservice.service;

import com.microservices.userservice.dto.*;


public interface AuthService {
    AuthResponseDto login(LoginDto dto);
    UserDtoResponse register(UserDtoRequest dto);
    void changePassword(ChangePasswordDto dto);
}