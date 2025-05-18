// src/main/java/com/elearning/rest/UserRestController.java
package com.elearning.rest;

import com.elearning.dto.UserDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.UserMapper;
import com.elearning.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;
    private final UserMapper mapper;

    public UserRestController(UserService service,
                              UserMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    /**
     * Récupère une page d'utilisateurs.
     * GET /api/users?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<UserDto>> list(Pageable pageable) {
        Page<UserDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    /**
     * Récupère un utilisateur par son ID.
     * GET /api/users/{id}
     * Renvoie 404 si introuvable.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
        UserDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Utilisateur introuvable pour l'ID " + id
                        )
                );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/etudiants")
    public ResponseEntity<List<UserDto>> listEtudiants() {
        // Va chercher tous les users qui ont le rôle "ETUDIANT"
        List<UserDto> dtos = service.findAllByRoleName("ETUDIANT")
                .stream()
                .map(mapper::toDto)
                .toList();


        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = (authentication != null) ? authentication.getName() : null;
        if (email == null) {
            return ResponseEntity.status(401).build();
        }
        UserDto dto = service.findByEmail(email)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Utilisateur introuvable pour l'email " + email
                        )
                );
        return ResponseEntity.ok(dto);
    }

}
