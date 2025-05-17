// src/main/java/com/elearning/service/FormationService.java
package com.elearning.service;

import com.elearning.model.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface FormationService {
    Page<Formation> findAll(Pageable pageable);
    Optional<Formation> findById(Long id);
    Formation save(Formation formation);
    void delete(Long id);
    long countAll();
}
