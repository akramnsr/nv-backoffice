package com.elearning.rest;

import com.elearning.dto.FormationDto;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.FormationMapperImpl;
import com.elearning.model.Formation;
import com.elearning.model.User;
import com.elearning.repository.FormationRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.FormationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formations")
public class FormationRestController {


    private final FormationService service;
    private final FormationMapperImpl mapper;
    private final UserRepository userRepository;
    private final FormationRepository formationRepository;

    public FormationRestController(FormationService service,
                                   FormationMapperImpl mapper,
                                   UserRepository userRepository,
                                   FormationRepository formationRepository) {
        this.service = service;
        this.mapper  = mapper;
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
    }
    @PostMapping("/{id}/commencer")
    public ResponseEntity<Void> commencerFormation(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmailWithFormations(email).orElseThrow();
        Formation formation = formationRepository.findById(id).orElseThrow();
        if (!user.getFormations().contains(formation)) {
            user.getFormations().add(formation);
            userRepository.save(user); // Enregistre la nouvelle association
        }
        return ResponseEntity.ok().build();
    }
    // Méthode de récupération paginée des formations (consultation uniquement)
    @GetMapping
    public ResponseEntity<Page<FormationDto>> list(Pageable pageable) {
        var page = service.findAll(pageable).map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormationDto> getOne(@PathVariable Long id) {
        var formationOpt = service.findById(id);
        System.out.println("Formation from service: " + formationOpt);

        var dto = formationOpt
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Formation introuvable pour l'ID " + id));

        System.out.println("DTO: " + dto);
        return ResponseEntity.ok(dto);
    }


}
