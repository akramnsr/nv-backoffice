// src/main/java/com/elearning/rest/ResultatRestController.java
package com.elearning.rest;

import com.elearning.dto.ResultatDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.ResultatMapper;
import com.elearning.service.ResultatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resultats")
public class ResultatRestController {

    private final ResultatService service;
    private final ResultatMapper mapper;

    public ResultatRestController(ResultatService service,
                                  ResultatMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    /**
     * Récupère une page de résultats.
     * GET /api/resultats?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<ResultatDto>> list(Pageable pageable) {
        Page<ResultatDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    /**
     * Récupère un résultat par son ID.
     * GET /api/resultats/{id}
     * Renvoie 404 si introuvable.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultatDto> getOne(@PathVariable Long id) {
        ResultatDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Résultat introuvable pour l'ID " + id
                        )
                );
        return ResponseEntity.ok(dto);
    }
}
