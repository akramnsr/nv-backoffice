package com.elearning.repository;

import com.elearning.model.RapportEtu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportEtuRepository extends JpaRepository<RapportEtu, Long> {
    // pas de méthode supplémentaire nécessaire pour count()
}
