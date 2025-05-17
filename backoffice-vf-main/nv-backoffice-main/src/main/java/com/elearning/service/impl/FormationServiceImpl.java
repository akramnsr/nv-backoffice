// src/main/java/com/elearning/service/impl/FormationServiceImpl.java
package com.elearning.service.impl;

import com.elearning.model.Formation;
import com.elearning.repository.FormationRepository;
import com.elearning.service.FormationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    private final FormationRepository repo;

    public FormationServiceImpl(FormationRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Formation> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<Formation> findById(Long id) {
        return Optional.of(repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation introuvable pour l'ID " + id))
        );
    }

    @Override
    public Formation save(Formation formation) {
        return repo.save(formation);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : formation introuvable pour l'ID " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public long countAll() {
        return repo.count();
    }
}
