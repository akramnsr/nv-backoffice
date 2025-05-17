// src/main/java/com/elearning/repository/RapportEtuRepository.java
package com.elearning.repository;

import com.elearning.model.RapportEtu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportEtuRepository extends JpaRepository<RapportEtu, Long> {
    // no extra methods needed
}
