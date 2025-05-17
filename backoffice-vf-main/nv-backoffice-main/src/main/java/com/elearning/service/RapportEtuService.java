package com.elearning.service;

import com.elearning.model.RapportEtu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RapportEtuService {
    Page<RapportEtu> findAll(Pageable pageable);
    Optional<RapportEtu> findById(Long id);
    RapportEtu save(RapportEtu rapportEtu);
    void delete(Long id);

    /** Nombre total de rapports (pour le dashboard admin) */
    long countAll();
}
