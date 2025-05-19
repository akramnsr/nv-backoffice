// src/main/java/com/elearning/rest/QuizRestController.java
package com.elearning.rest;

import com.elearning.dto.QuizDto;
import com.elearning.dto.QuizFullDto;
import com.elearning.mapper.QuizFullMapper;
import com.elearning.mapper.QuizMapper;
import com.elearning.model.Formation;
import com.elearning.model.Quiz;
import com.elearning.model.User;
import com.elearning.repository.UserRepository;
import com.elearning.service.QuizService;
import com.elearning.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    private final QuizService service;
    private final QuizMapper mapper;
    private final QuizFullMapper fullMapper;
    UserRepository userRepository;

    public QuizRestController(QuizService service, QuizMapper mapper, QuizFullMapper fullMapper, UserRepository userRepository) {
        this.service = service;
        this.mapper  = mapper;
        this.fullMapper = fullMapper;
        this.userRepository = userRepository;
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

    @GetMapping("/{id}/full")
    public ResponseEntity<QuizFullDto> getFull(@PathVariable Long id) {
        Quiz quiz = service.findByIdWithQuestions(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz introuvable pour l'ID " + id));
        QuizFullDto dto = fullMapper.toDto(quiz);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/mine")
    public List<QuizDto> getMyQuizzes(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmailWithFormations(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        // On récupère les formations suivies
        List<Formation> formations = user.getFormations();
        // On récupère les quiz pour ces formations
        List<Quiz> quizzes = formations.stream()
                .flatMap(f -> f.getQuizzes().stream())
                .toList();
        return quizzes.stream().map(mapper::toDto).toList();
    }


}
