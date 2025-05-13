package com.elearning.service;

import com.elearning.model.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FormationService {

    /**
     * Récupère une page de Formation (pagination + tri).
     */
    Page<Formation> findAll(Pageable pageable);

    /**
     * Recherche une Formation par son ID ou lève EntityNotFoundException.
     */
    Optional<Formation> findById(Long id);

    /**
     * Crée ou met à jour une Formation.
     */
    Formation save(Formation formation);

    /**
     * Supprime la Formation d’un ID donné ou lève EntityNotFoundException.
     */
    void delete(Long id);
}
