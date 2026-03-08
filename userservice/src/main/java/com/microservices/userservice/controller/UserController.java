package com.microservices.userservice.controller;

import com.microservices.userservice.dto.UserDtoRequest;
import com.microservices.userservice.dto.UserDtoResponse;
import com.microservices.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Créer un nouvel utilisateur
     * POST /api/users
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoResponse createUser(@RequestBody UserDtoRequest userRequest) {
        log.info("Creating user with email: {}", userRequest.getEmail());
        return userService.createUser(userRequest);
    }

    /**
     * Récupérer tous les utilisateurs
     * GET /api/users
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoResponse> getAllUsers() {
        log.info("Fetching all users");
        return userService.getAllUsers();
    }

    /**
     * Récupérer un utilisateur par ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer un utilisateur par email
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDtoResponse> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user with email: {}", email);
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Mettre à jour un utilisateur
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserDtoRequest userRequest) {
        log.info("Updating user with id: {}", id);
        return userService.updateUser(id, userRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprimer un utilisateur
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }

    /**
     * Vérifier si un email existe
     * GET /api/users/exists/{email}
     */
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        log.info("Checking if email exists: {}", email);
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * Health check endpoint
     * GET /api/users/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Service is UP and running!");
    }
}