package com.elearning.service;

import com.elearning.model.Resultat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ResultatService {
    /** CRUD standard */
    Page<Resultat> findAll(Pageable pageable);
    Optional<Resultat> findById(Long id);
    Resultat save(Resultat resultat);
    void delete(Long id);

    /** Pour le dashboard admin */
    long countAll();
}