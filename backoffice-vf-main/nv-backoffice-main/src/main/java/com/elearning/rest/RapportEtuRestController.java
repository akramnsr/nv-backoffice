// src/main/java/com/elearning/rest/RapportEtuRestController.java
package com.elearning.rest;

import com.elearning.dto.RapportEtuDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.RapportEtuMapper;
import com.elearning.service.RapportEtuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rapports")
public class RapportEtuRestController {

    private final RapportEtuService service;
    private final RapportEtuMapper mapper;

    public RapportEtuRestController(RapportEtuService service,
                                    RapportEtuMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    /**
     * Récupère une page de rapports étudiants.
     * GET /api/rapports?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<RapportEtuDto>> list(Pageable pageable) {
        Page<RapportEtuDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    /**
     * Récupère un rapport par son ID.
     * GET /api/rapports/{id}
     * Renvoie 404 si introuvable.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RapportEtuDto> getOne(@PathVariable Long id) {
        RapportEtuDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rapport introuvable pour l'ID " + id
                        )
                );
        return ResponseEntity.ok(dto);
    }
}
