// src/main/java/com/elearning/repository/FormationRepository.java
package com.elearning.repository;

import com.elearning.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    // plus de méthodes d’incrément, tout est calculé à la volée
}
