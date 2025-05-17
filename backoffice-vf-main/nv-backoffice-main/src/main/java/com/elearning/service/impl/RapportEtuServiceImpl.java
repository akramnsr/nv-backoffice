package com.elearning.service.impl;

import com.elearning.model.RapportEtu;
import com.elearning.repository.RapportEtuRepository;
import com.elearning.service.RapportEtuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RapportEtuServiceImpl implements RapportEtuService {

    private final RapportEtuRepository repo;

    public RapportEtuServiceImpl(RapportEtuRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<RapportEtu> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<RapportEtu> findById(Long id) {
        return Optional.of(
                repo.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Rapport non trouvé pour l'ID " + id)
                        )
        );
    }

    @Override
    public RapportEtu save(RapportEtu rapportEtu) {
        return repo.save(rapportEtu);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(
                    "Impossible de supprimer : rapport introuvable pour l'ID " + id
            );
        }
        repo.deleteById(id);
    }

    @Override
    public long countAll() {
        return repo.count();
    }
}
