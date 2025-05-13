// src/main/java/com/elearning/rest/RoleRestController.java
package com.elearning.rest;

import com.elearning.dto.RoleDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.RoleMapper;
import com.elearning.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleService service;
    private final RoleMapper mapper;

    public RoleRestController(RoleService service,
                              RoleMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    /**
     * Récupère une page de rôles.
     * GET /api/roles?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<RoleDto>> list(Pageable pageable) {
        Page<RoleDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    /**
     * Récupère un rôle par son ID.
     * GET /api/roles/{id}
     * Renvoie 404 si introuvable.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        RoleDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rôle introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }
}
