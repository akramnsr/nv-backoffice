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

    private final FormationRepository repository;

    public FormationServiceImpl(FormationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Formation> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Formation> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public Formation save(Formation formation) {
        return repository.save(formation);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : Formation introuvable pour l'ID " + id);
        }
        repository.deleteById(id);
    }
}
