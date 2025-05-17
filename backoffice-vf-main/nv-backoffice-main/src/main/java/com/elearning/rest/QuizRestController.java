// src/main/java/com/elearning/rest/QuizRestController.java
package com.elearning.rest;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizMapper;
import com.elearning.service.QuizService;
import com.elearning.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final QuizService service;
    private final QuizMapper mapper;

    public QuizRestController(QuizService service, QuizMapper mapper) {
        this.service = service;
        this.mapper  = mapper;
    }

    /**
     * Récupère une page de QuizDto.
     * Accessible via GET /api/quizzes?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<Page<QuizDto>> list(Pageable pageable) {
        Page<QuizDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    /**
     * Récupère un quiz par son ID.
     * Accessible via GET /api/quizzes/{id}
     * Lève 404 si l’ID n’existe pas.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getOne(@PathVariable Long id) {
        QuizDto dto = service.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Quiz introuvable pour l'ID " + id)
                );
        return ResponseEntity.ok(dto);
    }
}
