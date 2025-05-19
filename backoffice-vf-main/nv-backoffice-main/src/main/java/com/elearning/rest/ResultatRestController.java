// src/main/java/com/elearning/rest/ResultatRestController.java
package com.elearning.rest;

import com.elearning.dto.ResultatDto;
import com.elearning.dto.SubmitResultDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.ResultatMapper;
import com.elearning.model.Quiz;
import com.elearning.model.Resultat;
import com.elearning.model.User;
import com.elearning.repository.QuizRepository;
import com.elearning.repository.ResultatRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.ResultatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultats")
public class ResultatRestController {

    private final ResultatService service;
    private final ResultatMapper mapper;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final ResultatRepository resultatRepository; // Ajoute ceci si besoin

    public ResultatRestController(ResultatService service,
                                  ResultatMapper mapper,
                                  UserRepository userRepository,
                                  QuizRepository quizRepository,
                                  ResultatRepository resultatRepository) {
        this.service = service;
        this.mapper  = mapper;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.resultatRepository = resultatRepository;
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

    @PostMapping("/submit")
    public ResponseEntity<ResultatDto> submitResult(@RequestBody SubmitResultDto dto, Authentication auth) {
        String email = auth.getName(); // email de l'utilisateur connecté
        User etudiant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        // Facultatif : empêcher les doublons, ou bien gérer plusieurs tentatives

        Resultat resultat = new Resultat();
        resultat.setScore(dto.getScore());
        resultat.setDatePassage(new java.util.Date());
        resultat.setStatut("Passé");
        resultat.setEtudiant(etudiant);
        resultat.setQuiz(quiz);

        resultat = resultatRepository.save(resultat);

        return ResponseEntity.ok(mapper.toDto(resultat));
    }
    @GetMapping("/me")
    public List<ResultatDto> getMesResultats(Authentication auth) {
        String email = auth.getName();
        User etudiant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return resultatRepository.findByEtudiant(etudiant)
                .stream().map(mapper::toDto).toList();
    }

}
