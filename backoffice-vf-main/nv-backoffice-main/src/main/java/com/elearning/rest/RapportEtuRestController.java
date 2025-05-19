// src/main/java/com/elearning/rest/RapportEtuRestController.java
package com.elearning.rest;

import com.elearning.dto.RapportEtuDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.RapportEtuMapper;
import com.elearning.model.User;
import com.elearning.repository.RapportEtuRepository;  // AJOUTE
import com.elearning.repository.UserRepository;        // AJOUTE
import com.elearning.service.RapportEtuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
public class RapportEtuRestController {

    private final RapportEtuService service;
    private final RapportEtuMapper mapper;
    private final UserRepository userRepository;                  // AJOUTE
    private final RapportEtuRepository rapportEtuRepository;      // AJOUTE

    // Mets à jour le constructeur :
    public RapportEtuRestController(
            RapportEtuService service,
            RapportEtuMapper mapper,
            UserRepository userRepository,
            RapportEtuRepository rapportEtuRepository
    ) {
        this.service = service;
        this.mapper  = mapper;
        this.userRepository = userRepository;
        this.rapportEtuRepository = rapportEtuRepository;
    }

    @GetMapping
    public ResponseEntity<Page<RapportEtuDto>> list(Pageable pageable) {
        Page<RapportEtuDto> page = service.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

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

    // 🚩 Nouvel endpoint sécurisé : rapports de l’étudiant connecté
    @GetMapping("/me")
    public List<RapportEtuDto> getMesRapports(Authentication auth) {
        String email = auth.getName();
        User etudiant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return rapportEtuRepository.findByEtudiant(etudiant)
                .stream().map(mapper::toDto).toList();
    }

}
