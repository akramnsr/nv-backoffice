// src/main/java/com/elearning/repository/RoleRepository.java
package com.elearning.repository;

import com.elearning.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByNom(String nom);


}
