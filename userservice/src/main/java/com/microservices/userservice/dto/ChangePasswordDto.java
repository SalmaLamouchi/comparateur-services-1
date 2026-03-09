package com.microservices.userservice.dto;

public record ChangePasswordDto(String username, String oldPassword, String newPassword) {}