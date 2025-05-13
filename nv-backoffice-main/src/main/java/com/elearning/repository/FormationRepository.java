package com.elearning.repository;

import com.elearning.model.Formation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    // Ajoute cette méthode si elle n'existe pas déjà
    @Override
    @Transactional
    @Modifying
    void deleteById(Long id);
}
