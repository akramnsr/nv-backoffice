// src/main/java/com/elearning/repository/UserRepository.java
package com.elearning.repository;

import com.elearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleNom(String nom);
    long countByRoleNom(String nom);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.formations WHERE u.email = :email")
    Optional<User> findByEmailWithFormations(@Param("email") String email);

}
