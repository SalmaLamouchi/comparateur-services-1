package com.microservices.userservice.dto;


public record AuthResponseDto(String token, String username, java.util.Set<String> roles) {}