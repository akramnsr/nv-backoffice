// src/main/java/com/elearning/service/impl/ResultatServiceImpl.java
package com.elearning.service.impl;

import com.elearning.model.Resultat;
import com.elearning.repository.ResultatRepository;
import com.elearning.service.ResultatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ResultatServiceImpl implements ResultatService {

    private final ResultatRepository repo;

    public ResultatServiceImpl(ResultatRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Resultat> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<Resultat> findById(Long id) {
        return Optional.ofNullable(repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Résultat non trouvé pour l'ID " + id)));
    }

    @Override
    public Resultat save(Resultat resultat) {
        return repo.save(resultat);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(
                    "Impossible de supprimer : Résultat introuvable pour l'ID " + id);
        }
        repo.deleteById(id);
    }
}
